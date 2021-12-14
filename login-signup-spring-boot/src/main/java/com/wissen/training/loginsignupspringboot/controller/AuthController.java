package com.wissen.training.loginsignupspringboot.controller;


import com.wissen.training.loginsignupspringboot.dto.*;
import com.wissen.training.loginsignupspringboot.exception.UserAlreadyExistAuthenticationException;
import com.wissen.training.loginsignupspringboot.jwt.TokenProvider;
import com.wissen.training.loginsignupspringboot.service.LocalUserDetails;
import com.wissen.training.loginsignupspringboot.service.UserService;
import com.wissen.training.loginsignupspringboot.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        logger.info("loginRequest: "+loginRequest.toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        logger.info("Authentication: "+authentication.getDetails()+" principal: "+authentication.getPrincipal());
        LocalUserDetails localUser = (LocalUserDetails) authentication.getPrincipal();
        logger.info("jwt token: "+jwt);
        logger.info("local user: "+localUser);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        try{
            userService.registerNewUser(signUpRequest);
        }catch (UserAlreadyExistAuthenticationException userAlreadyExistAuthenticationException){
            return new ResponseEntity<>(new ApiResponse(false,"Email Already In Use"), HttpStatus.BAD_REQUEST);

        }
        return  ResponseEntity.ok().body(new ApiResponse(true,"User Created"));
    }
}
