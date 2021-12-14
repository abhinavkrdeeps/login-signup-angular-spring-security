package com.wissen.training.loginsignupspringboot.oauth2;

import com.wissen.training.loginsignupspringboot.jwt.TokenProvider;
import com.wissen.training.loginsignupspringboot.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.wissen.training.loginsignupspringboot.oauth2.CookieOAuth2RequestRepo.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private TokenProvider tokenProvider;
    private CookieOAuth2RequestRepo cookieOAuth2RequestRepo;

    @Autowired
    SuccessHandler(TokenProvider tokenProvider, CookieOAuth2RequestRepo requestRepo){
        this.tokenProvider=tokenProvider;
        this.cookieOAuth2RequestRepo=requestRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       String targetUrl = determineTargetUrl(request,response,authentication);
        if (response.isCommitted()) {
            this.logger.debug(LogMessage.format("Did not redirect to %s since response already committed.", targetUrl));
            return;
        }
       getRedirectStrategy().sendRedirect(request,response,targetUrl);

    }



    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookieFromName(request,REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = tokenProvider.createToken(authentication);
        return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token",token).build().toUriString();
    }


}
