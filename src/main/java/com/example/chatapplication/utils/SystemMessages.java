package com.example.chatapplication.utils;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.model.MessageBuilder;

public class SystemMessages {
    public static final Message welcome(String roomId, String username) {
        return new MessageBuilder()
                .newMessage()
                .toRoomId(roomId)
                .systemMessage().withContent(username + " joined :D");
    }

    public static final Message goodbye(String roomId, String username) {
        return new MessageBuilder()
                .newMessage()
                .toRoomId(roomId)
                .systemMessage().withContent(username + " left :(");
    }
}
