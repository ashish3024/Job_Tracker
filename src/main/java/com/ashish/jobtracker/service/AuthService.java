package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.LoginRequest;
import com.ashish.jobtracker.dto.request.RegisterRequest;
import com.ashish.jobtracker.dto.response.LoginResponse;
import com.ashish.jobtracker.dto.response.RegisterResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest registerRequest);
}