package com.relex.database.service;

import com.relex.database.repository.MessageRepository;
import com.relex.messenger.models.Chat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;

}
