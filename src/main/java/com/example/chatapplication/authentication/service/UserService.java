package com.example.chatapplication.authentication.service;

import com.example.chatapplication.authentication.model.User;

import javax.transaction.Transactional;

public interface UserService {
    @Transactional
    User createUser(User user);
}
