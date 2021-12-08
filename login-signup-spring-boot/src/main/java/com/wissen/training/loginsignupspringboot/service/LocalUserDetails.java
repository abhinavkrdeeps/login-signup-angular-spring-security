package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class LocalUserDetails implements UserDetails {

    private Long userId;
    private Set<Role> roles ;
    private String userName;
    private String password;

    public LocalUserDetails(Long userId,String userName,String password,Set<Role> roles){
        this.userName=userName;
        this.password=password;
        this.roles=roles;
        this.userId=userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
       for(Role role:this.roles){
           simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
       }
       return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
