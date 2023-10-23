package com.relex.security.dtos;

import lombok.Data;

@Data
public class RegistrationUserDto {

    private String nickname;
    private String name;
    private String surname;
    private String password;
    private String confirmPassword;
    private String mail;
}
