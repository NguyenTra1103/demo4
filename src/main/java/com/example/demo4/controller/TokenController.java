package com.example.demo4.controller;

import com.example.demo4.request.user.LoginRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.UserResponse;
import com.example.demo4.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("demo/v1/tokens")
@RequiredArgsConstructor
public class TokenController {
    @Autowired
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<ObjectResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse login = tokenService.login(loginRequest);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(),
                "LOGIN SUCCESS",
                login));
    }
}
