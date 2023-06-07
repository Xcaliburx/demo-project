package com.crud.project.service.impl;

import com.crud.project.model.Broker;
import com.crud.project.model.Developer;
import com.crud.project.model.User;
import com.crud.project.repository.BrokerRepository;
import com.crud.project.repository.DeveloperRepository;
import com.crud.project.repository.UserRepository;
import com.crud.project.request.LoginRequest;
import com.crud.project.request.RegisterRequest;
import com.crud.project.response.JwtResponse;
import com.crud.project.security.jwt.JwtUtils;
import com.crud.project.security.services.UserDetailsImpl;
import com.crud.project.security.services.UserDetailsServiceImpl;
import com.crud.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrokerRepository brokerRepository;

    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtUtils.generateToken(userDetails);

        UserDetailsImpl userData = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JwtResponse.builder()
                .token(jwt)
                .id(userData.getId())
                .email(userData.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Username is already taken";
        }

        if (request.getRole().equals("Broker") || request.getRole().equals("Developer")) {
            User user = new User(request.getEmail(),
                    encoder.encode(request.getPassword()),
                    request.getRole());

            userRepository.save(user);

            if (request.getRole().equals("Broker")) {
                Broker broker = new Broker();
                broker.setUser(user);
                broker.setName(request.getEmail());
                brokerRepository.save(broker);
            } else {
                Developer developer = new Developer();
                developer.setUser(user);
                developer.setName(request.getEmail());
                developerRepository.save(developer);
            }
            return "User Registered successfully";
        } else {
            return "Role is not valid";
        }
    }

    @Override
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).get();
    }
}
