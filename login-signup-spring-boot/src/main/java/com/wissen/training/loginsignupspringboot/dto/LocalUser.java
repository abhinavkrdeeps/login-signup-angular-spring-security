package com.wissen.training.loginsignupspringboot.dto;

import com.wissen.training.loginsignupspringboot.utils.GeneralUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class LocalUser extends User implements OAuth2User, OidcUser {

    private final OidcIdToken oidcIdToken;
    private final OidcUserInfo oidcUserInfo;
    private Map<String,Object> attributes;
    private com.wissen.training.loginsignupspringboot.models.User user;

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final com.wissen.training.loginsignupspringboot.models.User user) {
        this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user, null, null);
    }

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final com.wissen.training.loginsignupspringboot.models.User user, OidcIdToken idToken,
                     OidcUserInfo userInfo) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.oidcIdToken = idToken;
        this.oidcUserInfo = userInfo;
    }

  public static LocalUser create(com.wissen.training.loginsignupspringboot.models.User user, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
    LocalUser localUser = new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()),
            user, idToken, userInfo);
    localUser.setAttributes(attributes);
    return localUser;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }


  @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.oidcUserInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.oidcIdToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.user.getDisplay_name();
    }

    public com.wissen.training.loginsignupspringboot.models.User getUser(){
      return user;
    }
}
