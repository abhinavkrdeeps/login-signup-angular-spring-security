package com.wissen.training.loginsignupspringboot.jwt;

import com.wissen.training.loginsignupspringboot.config.AppProperties;
import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.service.LocalUserDetails;
import io.jsonwebtoken.*;
import jdk.nashorn.internal.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class TokenProvider {

    @Autowired
    private AppProperties appProperties;

    private Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public TokenProvider(final AppProperties appProperties){
        this.appProperties=appProperties;
    }

    public String createToken(Authentication authentication){
        LocalUserDetails userPrincipal = (LocalUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000000);
        System.out.println("tokenSecret: "+appProperties.getAuth().getTokenSecret());
        return Jwts.builder().setSubject(Long.toString(userPrincipal.getUserId())).setIssuedAt(new Date()).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, "A Very Secret Key").compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey("A Very Secret Key").parseClaimsJws(token).getBody();
        System.out.println("claims: "+claims);

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey("A Very Secret Key").parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }


}
