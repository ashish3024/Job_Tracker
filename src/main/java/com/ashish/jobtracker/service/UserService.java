package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.UserRequest;
import com.ashish.jobtracker.dto.response.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

}