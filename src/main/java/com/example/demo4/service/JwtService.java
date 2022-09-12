package com.example.demo4.service;

import com.example.demo4.contant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public interface JwtService {

    String generateToken(String email, Role role, String id);
    String testRole(String token);
    String parseTokenToId(String token);
    String parseTokenToEmail(String token);

}
