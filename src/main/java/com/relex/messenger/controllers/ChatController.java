package com.relex.messenger.controllers;


import com.relex.database.service.ChatService;
import com.relex.database.service.UserService;
import com.relex.messenger.models.Chat;
import com.relex.messenger.models.Message;
import com.relex.messenger.models.MessageSimple;
import com.relex.messenger.models.OutputMessage;
import com.relex.security.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Controller
@Setter
public class ChatController {

    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        String firstNickname = message.getSenderNickname();
        String secondNickname = message.getReceivingNickname();
        Chat chat = chatService.getChatUser(firstNickname, secondNickname);
        if (chat == null){
            chat = chatService.createNewChat(firstNickname, secondNickname);
        }
        chat.getMessages().add(message);
        chatService.saveChat(chat);
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSenderNickname());
        return message;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(MessageSimple message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
