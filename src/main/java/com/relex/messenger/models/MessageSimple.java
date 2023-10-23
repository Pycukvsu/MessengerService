package com.relex.messenger.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSimple {

    private String from;
    private String text;

    public MessageSimple(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public MessageSimple() {
    }

    @Override
    public String toString() {
        return "MessageSimple{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
