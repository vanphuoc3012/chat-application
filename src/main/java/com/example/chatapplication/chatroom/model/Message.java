package com.example.chatapplication.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String username;
    private String roomId;
    private LocalDateTime dateTime;
    private String fromUser;
    private String toUser;
    private String content;
}
