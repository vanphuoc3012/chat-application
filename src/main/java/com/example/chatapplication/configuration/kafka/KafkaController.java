package com.example.chatapplication.configuration.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {
    @Autowired
    private KafkaService kafkaService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka")
    public ResponseEntity<?> changeKafkaTopic(@RequestParam String topic) {
        try {
            kafkaService.changeTopic(topic);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Change listener to topic: " + topic);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka/create-topic")
    public ResponseEntity<?> createTopic(@RequestParam String topic) {
        kafkaService.createTopic(topic);
        return ResponseEntity.ok("Successfully create topic: " + topic);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/kafka/delete-topic")
    public ResponseEntity<?> deleteTopic(@RequestParam String topic) {
        kafkaService.deleteTopic(topic);
        return ResponseEntity.ok("Successfully delete topic: " + topic);
    }
}
