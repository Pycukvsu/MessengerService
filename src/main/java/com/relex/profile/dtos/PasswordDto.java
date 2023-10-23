package com.relex.profile.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

    private String lastPassword;
    private String newPassword;
    private String confirmNewPassword;

    public PasswordDto(String lastPassword, String newPassword, String confirmNewPassword) {
        this.lastPassword = lastPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public PasswordDto() {
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "lastPassword='" + lastPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}
