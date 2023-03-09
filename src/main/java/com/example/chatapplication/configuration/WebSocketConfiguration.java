package com.example.chatapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer {

    @Value("${chat.app.relay.host}")
    private String relayHost;
    @Value("${chat.app.relay.port}")
    private int relayPort;
    @Value("${chat.app.relay.username}")
    private String username;
    @Value("${chat.app.relay.password}")
    private String password;

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/room");
        registry.enableStompBrokerRelay("/topic/", "/queue/")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setSystemLogin(username)
                .setSystemPasscode(password)
                .setClientLogin(username)
                .setClientPasscode(password);
    }
}
