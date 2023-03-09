package com.example.chatapplication;

import com.example.chatapplication.chatroom.model.Room;
import com.example.chatapplication.chatroom.repository.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.util.Optional;

@DataRedisTest
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testCreateRoom() {
        String id = "456";
        String name = "Trò chuyện linh tinh";
        String description = "Truyện trò tâm sự đủ thứ";
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setDescription(description);

        Room savedRoom = roomRepository.save(room);
        Optional<Room> optionalRoom = roomRepository.findById(id);
        Assertions.assertThat(optionalRoom.isPresent()).isTrue();
    }
}
