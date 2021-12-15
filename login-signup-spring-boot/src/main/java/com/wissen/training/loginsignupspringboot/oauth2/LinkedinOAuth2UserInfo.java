package com.wissen.training.loginsignupspringboot.oauth2;

import com.wissen.training.loginsignupspringboot.models.OAuth2UserInfo;

import java.util.Map;

public class LinkedinOAuth2UserInfo extends OAuth2UserInfo {
    public LinkedinOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
