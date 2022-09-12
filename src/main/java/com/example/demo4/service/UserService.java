package com.example.demo4.service;

import com.example.demo4.domain.User;
import com.example.demo4.request.user.CreateUserRequest;
import com.example.demo4.request.user.UpdateUserRequest;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    User insertUserAdmin(CreateUserRequest userRequest);

    User insertUser(CreateUserRequest userRequest, String token) throws IOException;

    User updateUser(String token, UpdateUserRequest updateUserRequest, String id);

    User changeStatus(String id, UpdateUserRequest updateUserRequest, String token);

    Optional<User> findById(String id);

}
