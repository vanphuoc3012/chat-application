package com.example.chatapplication.chatroom.controller;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.model.Room;
import com.example.chatapplication.chatroom.model.RoomUser;
import com.example.chatapplication.chatroom.service.MessageService;
import com.example.chatapplication.chatroom.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class RoomController {
    private final Logger LOG = LoggerFactory.getLogger(RoomController.class);
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageService messageService;


    @Secured("ROLE_ADMIN")
    @PostMapping("/room")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(Room room) {
        return roomService.save(room);
    }

    @GetMapping("/room/{roomId}")
    public String join(@PathVariable(name = "roomId", required = false) String roomId,
                       Authentication authentication,
                       ModelMap model) {
        if(roomId == null) roomId = "123";
        List<Room> roomList = roomService.findAll();
        model.put("roomList", roomList);
        Room room = roomService.findById(roomId);
        model.put("room", room);
        return "chat";
    }

    @SubscribeMapping("/connected.users")
    public List<RoomUser> listChatRoomConnectedUsersOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = headerAccessor.getSessionAttributes().get("roomId").toString();
        List<RoomUser> roomUserList = roomService.findById(roomId).getConnectedUsers();

        LOG.info("Room {}, list room user size: {}", roomId, roomUserList.size());
        return roomUserList;
    }

    @SubscribeMapping("/old.messages")
    public List<Message> listOldMessageFromUserOnSubscribe(Authentication authentication,
                                                          SimpMessageHeaderAccessor headerAccessor) {
        String roomId = headerAccessor.getSessionAttributes().get("roomId").toString();
        return messageService.findAllMessageFor(roomId);
    }

    @MessageMapping("/send.message")
    public void sendMessage(@Payload Message message,
                            Authentication authentication,
                            SimpMessageHeaderAccessor headerAccessor) {
        String roomId = headerAccessor.getSessionAttributes().get("roomId").toString();
        String fromUser = authentication.getName();
        message.setRoomId(roomId);
        message.setFromUser(fromUser);

        roomService.sendPublicMessage(message);
    }
}
