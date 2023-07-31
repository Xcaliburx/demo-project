package com.crud.project.controller;

import com.crud.project.model.User;
import com.crud.project.request.LoginRequest;
import com.crud.project.request.RegisterRequest;
import com.crud.project.response.JwtResponse;
import com.crud.project.response.MessageResponse;
import com.crud.project.service.AuthService;
import com.crud.project.session.SessionConsumer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    SessionConsumer sessionConsumer;

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
    public ResponseEntity<JwtResponse> getUser(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        String sessionId = StringUtils.replace(jwtToken, "Bearer ", "");
        JwtResponse response = authService.getUser(sessionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout", description = "Logout")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        String sessionId = StringUtils.replace(jwtToken, "Bearer ", "");
        sessionConsumer.removeSessionById(sessionId);
        return ResponseEntity.ok(MessageResponse.builder().message("Success Logout").build());
    }

}
