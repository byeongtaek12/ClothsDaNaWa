package com.example.clothsdanawa.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthSignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String userRole;
}
