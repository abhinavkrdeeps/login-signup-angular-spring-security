package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.dto.SignUpRequest;
import com.wissen.training.loginsignupspringboot.exception.OAuth2AuthenticationProcessingException;
import com.wissen.training.loginsignupspringboot.exception.UserAlreadyExistAuthenticationException;
import com.wissen.training.loginsignupspringboot.models.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {

    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) throws UserAlreadyExistAuthenticationException, OAuth2AuthenticationProcessingException;


}
