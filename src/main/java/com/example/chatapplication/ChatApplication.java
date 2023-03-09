package com.example.chatapplication;

import com.example.chatapplication.chatroom.model.Message;
import com.example.chatapplication.chatroom.model.Room;
import com.example.chatapplication.chatroom.repository.RoomRepository;
import com.example.chatapplication.chatroom.service.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoomRepository roomRepository) {
        return args -> {
            Room room = new Room();
            room.setId("123");
            room.setName("Rao vặt");
            room.setDescription("Rao vặt đủ thứ");
            room.setConnectedUsers(new ArrayList<>());
            roomRepository.save(room);
        };
    }
}
