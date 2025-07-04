package org.example.authen.service;

import org.example.authen.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oauth2User;
    private User user;
    
    public CustomOAuth2User(OAuth2User oauth2User, User user) {
        this.oauth2User = oauth2User;
        this.user = user;
    }
    
    public CustomOAuth2User(Map<String, Object> attributes, User user) {
        this.user = user;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User != null ? oauth2User.getAttributes() : Collections.emptyMap();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User != null ? oauth2User.getAuthorities() : Collections.emptyList();
    }
    
    @Override
    public String getName() {
        return user.getName();
    }
    
    public User getUser() {
        return user;
    }
    
    public String getEmail() {
        return user.getEmail();
    }
}