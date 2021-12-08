package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.exception.ResourceNotFoundException;
import com.wissen.training.loginsignupspringboot.models.User;
import com.wissen.training.loginsignupspringboot.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger LOG  = LoggerFactory.getLogger(LocalUserDetailsService.class);


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("Not Found user with email: "+email);
        }
        LOG.info("user: "+user);
        return new LocalUserDetails(user.getId(),user.getDisplay_name(),user.getPassword(),user.getRoles()) ;
    }

    @Transactional
    public LocalUser loadUserById(Long id) throws ResourceNotFoundException {
        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("UserNot Found"));
        return createLocalUser(user);
    }

    /**
     * @param user
     * @return
     */
    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
    }
}
