package com.wissen.training.loginsignupspringboot.utils;

import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.dto.SocialProvider;
import com.wissen.training.loginsignupspringboot.dto.UserInfo;
import com.wissen.training.loginsignupspringboot.models.Role;
import com.wissen.training.loginsignupspringboot.models.User;
import com.wissen.training.loginsignupspringboot.service.LocalUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class GeneralUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralUtils.class);

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for(Role role:roles){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return simpleGrantedAuthorities;
    }
    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUserDetails localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        Set<Role> roleSet = new HashSet<>();
        for(String role:roles){
            Role role1= new Role();
            role1.setName(role);
            roleSet.add(role1);
        }
        User user = new User();
        LOG.info("localuser: "+localUser.getUsername());
        LOG.info("userId: "+localUser.getUserId());
        user.setDisplay_name(localUser.getUsername());
        user.setRoles(roleSet);
        return new UserInfo(localUser.getUserId().toString(), user.getDisplay_name(), user.getEmail(), roles);
    }
}
