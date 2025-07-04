package org.example.authen.controller;

import org.example.authen.model.User;
import org.example.authen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(createErrorResponse("User not authenticated"));
        }
        
        String email = principal.getAttribute("email");
        if (email != null) {
            User user = userService.getUserByEmail(email).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(createUserResponse(user));
            }
        }
        
        return ResponseEntity.ok(createErrorResponse("User not found"));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(createSuccessResponse("Logged out successfully"));
    }
    
    @GetMapping("/login/success")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(createErrorResponse("Authentication failed"));
        }
        
        String email = principal.getAttribute("email");
        return ResponseEntity.ok(createSuccessResponse("Login successful for: " + email));
    }
    
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", user);
        return response;
    }
    
    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }
    
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
}