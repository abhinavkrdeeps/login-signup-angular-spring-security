package com.wissen.training.loginsignupspringboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    private Logger LOG = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOG.error("UnAuthorized Error : "+authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getLocalizedMessage());
    }
}
