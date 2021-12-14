package com.wissen.training.loginsignupspringboot.controller;


import com.wissen.training.loginsignupspringboot.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("todos")
    public List<Todo> getUserTodos(HttpServletRequest httpServletRequest){

        logger.info("headers: "+httpServletRequest.getHeader("auth-token"));
        return Arrays.asList(
                new Todo("Complete Angular With Rxjs", new Date(), new Date()),
                new Todo("Complete React With Redux", new Date(), new Date())

        );
    }
}
