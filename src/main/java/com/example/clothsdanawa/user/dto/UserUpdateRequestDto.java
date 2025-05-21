package com.example.clothsdanawa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    private String name;
    private String email;
    private String password;
    private String address;
}