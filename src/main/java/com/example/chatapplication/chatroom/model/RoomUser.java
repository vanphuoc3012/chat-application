package com.example.chatapplication.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUser implements Comparable<RoomUser>{
    private String username;
    private LocalDateTime joinedAt;

    public RoomUser(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(RoomUser o) {
        return this.username.compareTo(o.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomUser roomUser = (RoomUser) o;
        return Objects.equals(username, roomUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
