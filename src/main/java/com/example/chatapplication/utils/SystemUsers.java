package com.example.chatapplication.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum SystemUsers {
    ADMIN("admin");
    private String username;

}
