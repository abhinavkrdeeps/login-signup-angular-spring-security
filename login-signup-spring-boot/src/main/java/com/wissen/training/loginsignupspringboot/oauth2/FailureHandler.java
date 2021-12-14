package com.wissen.training.loginsignupspringboot.oauth2;

import com.wissen.training.loginsignupspringboot.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.wissen.training.loginsignupspringboot.oauth2.CookieOAuth2RequestRepo.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    CookieOAuth2RequestRepo httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtils.getCookieFromName(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue).orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("noerror", exception.getMessage()).build().toUriString();

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
