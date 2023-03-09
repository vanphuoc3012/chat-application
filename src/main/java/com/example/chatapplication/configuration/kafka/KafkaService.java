package com.example.chatapplication.configuration.kafka;

import com.example.chatapplication.chatroom.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaService {
    @Autowired
    private AdminClient adminClient;
    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, Message> listenerContainerFactory;
    @Autowired
    ConsumerFactory<String, Message> consumerFactory;
    ConcurrentMessageListenerContainer<String, Message> listenerContainer;
    public void changeTopic(String topic) throws InterruptedException {
        log.info("Changing topic to: {}", topic);
        if(listenerContainer != null) {
            listenerContainer.stop();
            Thread.sleep(2000);
            listenerContainer.destroy();
            Thread.sleep(2000);
        }
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setGroupId(RandomStringUtils.randomAlphanumeric(3));
        containerProperties.setMessageListener((MessageListener<String, Message>) message -> {
            System.out.println("Kafka listener, topic: " + message.topic().toString() + ", message content: " + message.value().getContent());
        });
        listenerContainer = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        listenerContainer.start();
    }

    public void createTopic(String topic) {
        adminClient.createTopics(List.of(TopicBuilder.name(topic).build()));
    }

    public void deleteTopic(String topic) {
        adminClient.deleteTopics(List.of(topic));
    }
}
