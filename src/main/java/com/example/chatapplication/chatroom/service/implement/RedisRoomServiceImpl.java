package com.example.chatapplication.chatroom.service.implement;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.model.Room;
import com.example.chatapplication.chatroom.model.RoomUser;
import com.example.chatapplication.chatroom.repository.RoomRepository;
import com.example.chatapplication.chatroom.service.MessageService;
import com.example.chatapplication.chatroom.service.RoomService;
import com.example.chatapplication.configuration.kafka.KafkaTopics;
import com.example.chatapplication.utils.Destinations;
import com.example.chatapplication.utils.SystemMessages;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class RedisRoomServiceImpl implements RoomService {
    private final Logger LOG = LoggerFactory.getLogger(RedisRoomServiceImpl.class);
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate websocketTemplate;
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Override
    public List<Room> findAll() {
        LOG.info("Getting all room");
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        LOG.info("Create new room, name: {}" + room.getName());
        room.setId(RandomStringUtils.randomAlphanumeric(5));
        return roomRepository.save(room);
    }

    @Override
    public Room findById(String id) {
        LOG.info("Finding room id: {}", id);
        return roomRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Room not found")
        );
    }

    @Override
    public Room join(RoomUser joiningUser, Room room) {
        LOG.info("User -{}- is joining room id -{}-", joiningUser.getUsername(), room.getId());
        room.addUser(joiningUser);
        roomRepository.save(room);

        Message message = SystemMessages.welcome(room.getId(), joiningUser.getUsername());
        //send message to room
        sendPublicMessage(message);
        LOG.info("Message content: {}", message.getContent());
        //update connected user via websocket
        updateConnectedUsersViaWebsocket(room);
        return room;
    }

    @Override
    public Room leave(RoomUser leavingUser, Room room) {
        //send bye bye message
        sendPublicMessage(SystemMessages.goodbye(room.getId(), leavingUser.getUsername()));

        room.removeUser(leavingUser);
        roomRepository.save(room);

        //update connected user via websocket
        updateConnectedUsersViaWebsocket(room);
        return room;
    }

    @Override
    public void sendPublicMessage(Message message) {
        message.setDateTime(LocalDateTime.now());
        String destination = Destinations.Room.publicMessages(message.getRoomId());
        LOG.info("Sending public message to: {}", destination);
        websocketTemplate.convertAndSend(
                destination,
                message);
        if(!message.getFromUser().equals("admin")) {
            messageService.appendMessageToConversations(message);
            kafkaTemplate.send(KafkaTopics.TOPIC1, message);
            kafkaTemplate.send(KafkaTopics.TOPIC2, message);
        }

    }

    @Override
    public void updateConnectedUsersViaWebsocket(Room room) {
        String destination = Destinations.Room.connectedUsers(room.getId());
        LOG.info("Updating connected user to: {}", destination);
        websocketTemplate.convertAndSend(
                destination,
                room.getConnectedUsers());
    }
}
