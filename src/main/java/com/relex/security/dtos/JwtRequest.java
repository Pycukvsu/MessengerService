package com.relex.security.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String mail;
    private String password;
}
