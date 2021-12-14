package com.wissen.training.loginsignupspringboot.oauth2;

import com.wissen.training.loginsignupspringboot.exception.OAuth2AuthenticationProcessingException;
import com.wissen.training.loginsignupspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
         OAuth2User oAuth2User = super.loadUser(userRequest);
         try {
             System.out.println("email :"+oAuth2User.getName());
             return userService.processUserRegistration(
                     userRequest.getClientRegistration().getRegistrationId(), userRequest.getAdditionalParameters(), null, null);
         }catch (OAuth2AuthenticationProcessingException exception){
             try {
                 throw  exception;
             } catch (OAuth2AuthenticationProcessingException e) {
                 e.printStackTrace();
             }
         }catch (Exception e){
             throw new OAuth2AuthenticationException(e.getMessage());
         }

        return super.loadUser(userRequest);
    }
}
