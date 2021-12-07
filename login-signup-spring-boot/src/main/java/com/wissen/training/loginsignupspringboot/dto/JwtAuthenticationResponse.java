package com.wissen.training.loginsignupspringboot.dto;


public class JwtAuthenticationResponse {

    private String accessToken;
    private UserInfo user;

    public JwtAuthenticationResponse(String jwt, UserInfo buildUserInfo) {
    }
}
