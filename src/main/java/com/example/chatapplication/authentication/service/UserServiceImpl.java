package com.example.chatapplication.authentication.service;

import com.example.chatapplication.authentication.model.Role;
import com.example.chatapplication.authentication.model.User;
import com.example.chatapplication.authentication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public User createUser(User user) {
        LOGGER.info("Creating new user with username: {}", user.getUsername());
        user.setOnline(false);
        user.addRoles(Set.of(new Role(4)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
