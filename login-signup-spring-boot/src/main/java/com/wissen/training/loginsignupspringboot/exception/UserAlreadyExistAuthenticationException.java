package com.wissen.training.loginsignupspringboot.exception;

import com.wissen.training.loginsignupspringboot.models.User;

public class UserAlreadyExistAuthenticationException extends Exception {

    public UserAlreadyExistAuthenticationException(String message){
        super(message);
    }
}
