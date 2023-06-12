package com.crud.project.controller;

import com.crud.project.model.User;
import com.crud.project.request.LoginRequest;
import com.crud.project.request.RegisterRequest;
import com.crud.project.response.JwtResponse;
import com.crud.project.response.MessageResponse;
import com.crud.project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Login User", description = "Login to access data")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Register User", description = "Register New User")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        String message = authService.register(request);
        if (message.equals("Username is already taken") || message.equals("Role is not valid")) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder().message(message).build());
        }
        return ResponseEntity.ok(MessageResponse.builder().message(message).build());
    }

    @Operation(summary = "Get User", description = "Get Logged In User Data")
    @GetMapping("/user")
    public ResponseEntity<JwtResponse> getUser() {
        User user = authService.getUser();
        JwtResponse response = JwtResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRole())
                .build();
        return ResponseEntity.ok(response);
    }
}
