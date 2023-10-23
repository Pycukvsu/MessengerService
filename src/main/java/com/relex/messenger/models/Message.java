package com.relex.messenger.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "sender_username")
    private String senderNickname;
    private String receivingNickname;
    @Column(name = "message")
    private String message;

    public Message(String senderNickname, String receivingNickname, String message) {
        this.senderNickname = senderNickname;
        this.receivingNickname = receivingNickname;
        this.message = message;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId=" + senderNickname +
                ", message='" + message + '\'' +
                '}';
    }
}