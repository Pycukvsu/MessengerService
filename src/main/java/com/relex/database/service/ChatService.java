package com.relex.database.service;

import com.relex.database.repository.ChatRepository;
import com.relex.messenger.models.Chat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ChatService {

    private ChatRepository chatRepository;

    public Chat getChatUser(String firstNickname, String secondNickname) {
        Chat chat = chatRepository.findByFirstUsernameAndSecondUsername(
                firstNickname,
                secondNickname
        );
        if (chat != null) {
            return chat;
        }
        chat = chatRepository.findByFirstUsernameAndSecondUsername(
                secondNickname,
                firstNickname
        );
        return chat;
    }

    public Chat createNewChat(String firstNickname, String secondNickname) {
        return new Chat(firstNickname, secondNickname);
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }
}
