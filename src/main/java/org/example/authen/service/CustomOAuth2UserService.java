package org.example.authen.service;

import org.example.authen.model.AuthProvider;
import org.example.authen.model.User;
import org.example.authen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        try {
            return processOAuth2User(userRequest, oauth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException("Error processing OAuth2 user");
        }
    }
    
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String pictureUrl = oauth2User.getAttribute("picture");
        
        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, name, pictureUrl, registrationId);
        } else {
            user = createNewUser(email, name, pictureUrl, registrationId);
        }
        
        return new CustomOAuth2User(oauth2User.getAttributes(), user);
    }
    
    private User createNewUser(String email, String name, String pictureUrl, String registrationId) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPictureUrl(pictureUrl);
        user.setProvider(getAuthProvider(registrationId));
        user.setProviderId(null);
        
        return userRepository.save(user);
    }
    
    private User updateExistingUser(User user, String name, String pictureUrl, String registrationId) {
        user.setName(name);
        user.setPictureUrl(pictureUrl);
        user.setProvider(getAuthProvider(registrationId));
        
        return userRepository.save(user);
    }
    
    private AuthProvider getAuthProvider(String registrationId) {
        if ("google".equals(registrationId)) {
            return AuthProvider.GOOGLE;
        } else if ("facebook".equals(registrationId)) {
            return AuthProvider.FACEBOOK;
        } else {
            return AuthProvider.LOCAL;
        }
    }
}