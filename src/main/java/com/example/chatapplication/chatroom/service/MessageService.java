package com.example.chatapplication.chatroom.service;

import com.example.chatapplication.chatroom.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAllMessageFor(String username);

    void appendMessageToConversations(Message message);
}
