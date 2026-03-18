package com.ashish.jobtracker.controller.auth;

import com.ashish.jobtracker.dto.request.LoginRequest;
import com.ashish.jobtracker.dto.request.RegisterRequest;
import com.ashish.jobtracker.dto.response.LoginResponse;
import com.ashish.jobtracker.dto.response.RegisterResponse;
import com.ashish.jobtracker.security.JwtUtil;
import com.ashish.jobtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import com.ashish.jobtracker.security.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

      return authService.login(request);
    }
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

}