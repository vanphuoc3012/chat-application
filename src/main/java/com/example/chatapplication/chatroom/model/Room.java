package com.example.chatapplication.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private String id;
    private String name;
    private String description;
    private List<RoomUser> connectedUsers = new ArrayList<>();

    public int getNumberOfConnectedUsers() {
        return this.connectedUsers.size();
    }

    public void addUser(RoomUser roomUser) {
        connectedUsers.add(roomUser);
    }

    public void removeUser(RoomUser roomUser) {
        connectedUsers.removeAll(List.of(roomUser));
    }
}
