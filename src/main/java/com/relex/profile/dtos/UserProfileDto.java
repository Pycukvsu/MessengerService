package com.relex.profile.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

    private String nickname;
    private String name;
    private String surname;
    private String mail;

    public UserProfileDto(String nickname, String name, String surname, String mail) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
    }

    public UserProfileDto() {
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
