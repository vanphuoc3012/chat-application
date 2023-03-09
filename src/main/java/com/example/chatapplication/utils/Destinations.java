package com.example.chatapplication.utils;

public class Destinations {
    public static class Room {
        public static String publicMessages(String roomId) {
            return "/topic/" + roomId + ".public.messages";
        }

        public static String privateMessage(String roomId) {
            return "/queue/" + roomId + ".private.messages";
        }

        public static String connectedUsers(String roomId) {
            return "/topic/" + roomId + ".connected.users";
        }
    }
}
