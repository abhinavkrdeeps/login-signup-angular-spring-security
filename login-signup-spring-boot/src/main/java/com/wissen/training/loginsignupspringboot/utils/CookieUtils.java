package com.wissen.training.loginsignupspringboot.utils;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CookieUtils {

    public static Optional<Cookie> getCookieFromName(HttpServletRequest httpServletRequest, String name){
        Cookie [] cookies = httpServletRequest.getCookies();
        List<Cookie> filtered =  Stream.of(cookies).filter(cookie->cookie.getName().equals(name)).collect(Collectors.toList());
        return (filtered.size()==1)?Optional.of(filtered.get(0)):Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String name){
        Cookie [] cookies = httpServletRequest.getCookies();
        Stream.of(cookies).filter(cookie -> cookie.getName().equals(name)).map(cookie -> {
            cookie.setMaxAge(0);
            cookie.setValue("");
            cookie.setPath("");
            httpServletResponse.addCookie(cookie);
            return cookie;
        });
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
