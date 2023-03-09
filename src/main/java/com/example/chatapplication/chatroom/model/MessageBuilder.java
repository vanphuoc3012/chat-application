package com.example.chatapplication.chatroom.model;

import com.example.chatapplication.utils.SystemUsers;

import java.time.LocalDateTime;

public class MessageBuilder {

    private Message message;
    private MessageChatRoom messageChatRoom;
    private MessageType messageType;
    private MessageToUser messageToUser;
    private MessageFromUser messageFromUser;
    private MessageText messageText;

    public MessageChatRoom newMessage() {
        message = new Message();
        messageChatRoom = new MessageChatRoom();
        return messageChatRoom;
    }

    public class MessageChatRoom {
        public MessageType toRoomId(String roomId) {
            message.setRoomId(roomId);
            messageType = new MessageType();
            return messageType;
        }
    }

    public class MessageType {
        public MessageText systemMessage() {
            message.setFromUser(SystemUsers.ADMIN.getUsername());
            messageText = new MessageText();
            return messageText;
        }

        public MessageFromUser publicMessage() {
            message.setToUser(null);
            messageFromUser = new MessageFromUser();
            return messageFromUser;
        }

        public MessageToUser privateMessage() {
            messageToUser = new MessageToUser();
            return messageToUser;
        }
    }

    public class MessageToUser {
        public MessageFromUser toUser(String username) {
            message.setToUser(username);
            messageFromUser = new MessageFromUser();
            return messageFromUser;
        }
    }

    public class MessageFromUser {
        public MessageText fromUser(String username) {
            message.setFromUser(username);
            messageText = new MessageText();
            return messageText;
        }
    }

    public class MessageText {
        public Message withContent(String content) {
            message.setContent(content);
            message.setDateTime(LocalDateTime.now());
            return message;
        }
    }
}
