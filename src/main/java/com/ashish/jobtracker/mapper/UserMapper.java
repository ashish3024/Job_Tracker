package com.ashish.jobtracker.mapper;

import com.ashish.jobtracker.dto.response.UserResponse;
import com.ashish.jobtracker.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

}