package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.dto.SignUpRequest;
import com.wissen.training.loginsignupspringboot.exception.UserAlreadyExistAuthenticationException;
import com.wissen.training.loginsignupspringboot.models.Role;
import com.wissen.training.loginsignupspringboot.models.User;
import com.wissen.training.loginsignupspringboot.repository.RoleRepository;
import com.wissen.training.loginsignupspringboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if(signUpRequest!=null && signUpRequest.getUserId()!=null && userRepository.existsByEmail(signUpRequest.getEmail()) ){
            throw new UserAlreadyExistAuthenticationException("User With Email : "+signUpRequest.getEmail()+" Already Exists");
        }

        assert signUpRequest != null;
        User user = buildUser(signUpRequest);
        Date now = Calendar.getInstance().getTime();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    private User buildUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setDisplay_name(signUpRequest.getDisplayName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Set<Role> roleList = new HashSet<>();
        roleList.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roleList);
        user.setProvider("LOCAL");
        user.setProviderUserId("1");
        return user;

    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        LOG.info("user by email: "+user);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        return null;
    }
}
