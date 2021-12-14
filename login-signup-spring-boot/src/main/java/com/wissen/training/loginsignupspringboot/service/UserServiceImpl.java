package com.wissen.training.loginsignupspringboot.service;

import com.wissen.training.loginsignupspringboot.dto.LocalUser;
import com.wissen.training.loginsignupspringboot.dto.SignUpRequest;
import com.wissen.training.loginsignupspringboot.dto.SocialProvider;
import com.wissen.training.loginsignupspringboot.exception.OAuth2AuthenticationProcessingException;
import com.wissen.training.loginsignupspringboot.exception.UserAlreadyExistAuthenticationException;
import com.wissen.training.loginsignupspringboot.models.OAuth2UserInfo;
import com.wissen.training.loginsignupspringboot.models.Role;
import com.wissen.training.loginsignupspringboot.models.User;
import com.wissen.training.loginsignupspringboot.oauth2.OAuth2UserInfoFactory;
import com.wissen.training.loginsignupspringboot.repository.RoleRepository;
import com.wissen.training.loginsignupspringboot.repository.UserRepository;
import com.wissen.training.loginsignupspringboot.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
       return  userRepository.findById(id);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) throws UserAlreadyExistAuthenticationException, OAuth2AuthenticationProcessingException {
        System.out.println("registrationId :"+registrationId+" attributes: "+attributes);
        System.out.println("OidcToken: "+idToken+" userinfo: "+userInfo);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        System.out.println("OAuth2UserInfo Name: "+oAuth2UserInfo.getName() +" OAuth2UserInfo email: "+oAuth2UserInfo.getEmail());
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        System.out.println("userDetails: "+userDetails);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplay_name(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.getBuilder().addProviderUserId(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
                .addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
    }
}
