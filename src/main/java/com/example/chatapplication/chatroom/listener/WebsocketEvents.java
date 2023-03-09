package com.example.chatapplication.chatroom.listener;

import com.example.chatapplication.chatroom.model.RoomUser;
import com.example.chatapplication.chatroom.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

@Component
@Slf4j
public class WebsocketEvents {
    private final Logger LOG = LoggerFactory.getLogger(WebsocketEvents.class);
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    private RoomService roomService;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headerAccessor.getNativeHeader("roomId").get(0);
        LOG.info("Handling session connect event, room id: {}", roomId);
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        RoomUser roomUser = new RoomUser(event.getUser().getName(), LocalDateTime.now());

        roomService.join(roomUser, roomService.findById(roomId));
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headerAccessor.getSessionAttributes().get("roomId").toString();
        LOG.info("Handling user disconnect event, room id: {}", roomId);
        RoomUser leavingUser = new RoomUser(event.getUser().getName());

        roomService.leave(leavingUser, roomService.findById(roomId));
    }
}
