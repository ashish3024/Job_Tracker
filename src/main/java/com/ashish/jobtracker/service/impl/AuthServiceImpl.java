package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.LoginRequest;
import com.ashish.jobtracker.dto.request.RegisterRequest;
import com.ashish.jobtracker.dto.response.LoginResponse;
import com.ashish.jobtracker.dto.response.RegisterResponse;
import com.ashish.jobtracker.entity.User;

import com.ashish.jobtracker.entity.constant.UserRole;
import com.ashish.jobtracker.exception.InvalidCredentialsException;
import com.ashish.jobtracker.repository.UserRepository;
import com.ashish.jobtracker.security.JwtUtil;
import com.ashish.jobtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }


        String token = jwtUtil.generateToken(request.getEmail());

        return new LoginResponse(token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new RegisterResponse(token);
    }
}