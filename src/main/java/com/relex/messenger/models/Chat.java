package com.relex.messenger.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_username")
    private String firstUsername;
    @Column(name = "second_username")
    private String secondUsername;
    @ManyToMany
    @JoinTable(
            name = "messages",
            joinColumns = @JoinColumn(name = "chats_id")
    )
    private Collection<Message> messages;

    public Chat(String firstUsername, String secondUsername) {
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
    }

    public Chat() {
    }

    @Override
    public String toString() {
        return "Chat{" +
                "firstUsername='" + firstUsername + '\'' +
                ", secondUsername='" + secondUsername + '\'' +
                '}';
    }
}
