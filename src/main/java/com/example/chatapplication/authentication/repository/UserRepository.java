package com.example.chatapplication.authentication.repository;

import com.example.chatapplication.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
