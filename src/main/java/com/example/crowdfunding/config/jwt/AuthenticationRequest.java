package com.example.crowdfunding.config.jwt;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String userName;
    private String password;

    public AuthenticationRequest() {
    }
}
