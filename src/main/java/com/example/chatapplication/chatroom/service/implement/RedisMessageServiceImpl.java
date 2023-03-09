package com.example.chatapplication.chatroom.service.implement;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RedisMessageServiceImpl implements MessageService {
    private final Logger LOG = LoggerFactory.getLogger(RedisRoomServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Message> redisTemplate;

    @Override
    public List<Message> findAllMessageFor(String roomId) {
        List<Message> messageList = redisTemplate.opsForList().range(roomId, 0, -1);
        LOG.info("Getting message list for room: {}, size: {}", roomId, messageList.size());
        return messageList;
    }

    @Override
    public void appendMessageToConversations(Message message) {
        LOG.info("Appending message to conversations, room id: {}" + message.getRoomId());
        redisTemplate.opsForList().rightPush(message.getRoomId(), message);
    }
}
