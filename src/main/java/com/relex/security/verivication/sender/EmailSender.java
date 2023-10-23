package com.relex.security.verivication.sender;

import com.relex.models.User;

import javax.mail.MessagingException;

public interface EmailSender {
    void sendSimpleEmail(User user, String token) throws MessagingException;
}
