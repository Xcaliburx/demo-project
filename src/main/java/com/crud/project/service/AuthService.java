package com.crud.project.service;

import com.crud.project.model.User;
import com.crud.project.request.LoginRequest;
import com.crud.project.request.RegisterRequest;
import com.crud.project.response.JwtResponse;

public interface AuthService {

    JwtResponse login(LoginRequest request);

    String register(RegisterRequest request);

    User getUser();
}
