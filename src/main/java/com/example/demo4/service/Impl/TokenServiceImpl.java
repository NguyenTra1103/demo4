package com.example.demo4.service.Impl;

import com.example.demo4.contant.UserStatus;
import com.example.demo4.domain.User;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.UserRepository;
import com.example.demo4.request.user.LoginRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.UserResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        if (!user.get().getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_PASSWORD_INCORRECT);
        }
        if (user.get().getUserStatus().equals(UserStatus.INACTIVE)){
            throw new CustomException(ObjectResponse.STATUS_CODE_FORBIDDEN,ObjectResponse.MESSAGE_USER_ACCOUNT_INACTIVE);
        }

        String token = jwtService.generateToken(user.get().getEmail(),user.get().getRole(), user.get().getId());
        UserResponse response = new UserResponse(user.get().getId(), user.get().getEmail(), user.get().getRole(),token);
        return response;
    }
}
