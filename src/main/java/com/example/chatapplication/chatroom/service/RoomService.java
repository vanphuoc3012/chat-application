package com.example.chatapplication.chatroom.service;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.model.Room;
import com.example.chatapplication.chatroom.model.RoomUser;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    Room save(Room room);

    Room findById(String id);

    Room join(RoomUser joiningUser, Room room);

    Room leave(RoomUser leavingUser, Room room);

    void sendPublicMessage(Message message);

    void updateConnectedUsersViaWebsocket(Room room);
}
