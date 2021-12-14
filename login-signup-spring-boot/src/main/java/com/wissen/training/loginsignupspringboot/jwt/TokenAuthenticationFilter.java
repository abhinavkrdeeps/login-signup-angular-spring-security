package com.wissen.training.loginsignupspringboot.jwt;

import com.wissen.training.loginsignupspringboot.service.LocalUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private LocalUserDetailsService localUserDetailsService;

    private final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwtToken = getTokenFromRequest(request);
            logger.info("jwt token in token auth filter: "+jwtToken);
            if(tokenProvider.validateToken(jwtToken)){
                Long userId = tokenProvider.getUserIdFromToken(jwtToken);
                System.out.println(" userID: "+userId);
                UserDetails userDetails = localUserDetailsService.loadUserById(userId);
                System.out.println("userDetails: "+userDetails);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }catch (Exception exception){
            logger.error("Failed To Parse Jwt Token: "+exception);

        }
        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;

    }
}
