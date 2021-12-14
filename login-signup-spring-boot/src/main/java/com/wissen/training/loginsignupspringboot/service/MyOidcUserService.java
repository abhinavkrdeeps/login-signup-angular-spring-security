package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.exception.OAuth2AuthenticationProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class MyOidcUserService extends OidcUserService {

    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest: "+userRequest.getAdditionalParameters());
        OidcUser oidcUser = super.loadUser(userRequest);
        try{
            System.out.println("Oidc User: "+oidcUser);
            return userService.processUserRegistration(
                    userRequest.getClientRegistration().getRegistrationId(),
                    userRequest.getAdditionalParameters(),
                    oidcUser.getIdToken(),
                    oidcUser.getUserInfo()
            );
        }catch (Exception | OAuth2AuthenticationProcessingException ex){
            System.out.println("Throwing Exception: "+ex.getMessage());
            throw new OAuth2AuthenticationException("Exception while user Registration");

        }
    }
}
