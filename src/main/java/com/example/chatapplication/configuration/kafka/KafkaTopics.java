package com.example.chatapplication.configuration.kafka;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopics {
    public static final String TOPIC1 = "message-topic1";
    public static final String TOPIC2 = "message-topic2";
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(TOPIC1).build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(TOPIC2).build();
    }

    @Bean
    public AdminClient kafkaAdminClient() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return AdminClient.create(configurations);
    }
}
