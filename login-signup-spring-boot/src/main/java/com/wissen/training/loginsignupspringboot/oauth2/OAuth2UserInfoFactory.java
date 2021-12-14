package com.wissen.training.loginsignupspringboot.oauth2;

import java.util.Map;

import com.wissen.training.loginsignupspringboot.dto.SocialProvider;
import com.wissen.training.loginsignupspringboot.exception.OAuth2AuthenticationProcessingException;
import com.wissen.training.loginsignupspringboot.models.OAuth2UserInfo;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
        if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
//        } else if (registrationId.equalsIgnoreCase(SocialProvider.FACEBOOK.getProviderType())) {
//            return new FacebookOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(SocialProvider.GITHUB.getProviderType())) {
//            return new GithubOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(SocialProvider.LINKEDIN.getProviderType())) {
//            return new LinkedinOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(SocialProvider.TWITTER.getProviderType())) {
//            return new GithubOAuth2UserInfo(attributes);
//        }
//
//
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
        }
}