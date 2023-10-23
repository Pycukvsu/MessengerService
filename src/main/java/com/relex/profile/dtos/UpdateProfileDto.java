package com.relex.profile.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDto {
    private String nickname;
    private String name;
    private String surname;
    private String newMail;

    public UpdateProfileDto(String nickname, String name, String surname) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
    }

    public UpdateProfileDto() {
    }

    @Override
    public String toString() {
        return "UpdateProfile{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
