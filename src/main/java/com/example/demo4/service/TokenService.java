package com.example.demo4.service;

import com.example.demo4.request.user.LoginRequest;
import com.example.demo4.response.UserResponse;

public interface TokenService {
    UserResponse login(LoginRequest loginRequest);
}
