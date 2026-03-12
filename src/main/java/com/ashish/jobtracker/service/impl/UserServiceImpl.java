package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.UserRequest;
import com.ashish.jobtracker.dto.response.UserResponse;
import com.ashish.jobtracker.entity.User;
import com.ashish.jobtracker.mapper.UserMapper;
import com.ashish.jobtracker.repository.UserRepository;
import com.ashish.jobtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse registerUser(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
}