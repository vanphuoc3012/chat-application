package com.example.chatapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .mvcMatchers("/login", "/new-account", "/ws/*").permitAll()
                .mvcMatchers("/webjars/**", "/js/**", "/css/*", "/images/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
