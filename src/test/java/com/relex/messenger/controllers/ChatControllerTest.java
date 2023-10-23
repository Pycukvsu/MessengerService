package com.relex.messenger.controllers;

import com.relex.database.service.ChatService;
import com.relex.messenger.models.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ChatControllerTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatController chatController;

    @Test
    void sendMessage() {
        Message message = new Message("Sad", "a", "Hello!");
        Message message2 = new Message("a", "Sad", "Hello! How you?");

        chatController.sendMessage(message);
        chatController.sendMessage(message2);
    }
}