package com.example.chatapplication.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class JsonConfiguration {
    @Bean
    public Module jsr310Module() {
        return new JSR310Module();
    }
}
