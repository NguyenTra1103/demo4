package com.example.demo4.service.Impl;

import com.example.demo4.contant.Role;
import com.example.demo4.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

     private static final String KEY = "a-very-secure-server-key-of-phoenix";
    @Override
    public String generateToken(String email, Role role, String id) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", email);
    claims.put("role", role);
    claims.put("id",id);
    String token = Jwts.builder()
            .addClaims(claims)
            .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
            .compact();
    return token;
}

@Override
    public String testRole(String token) {
        if(token.startsWith("Bearer ")){
            token = token.replace("Bearer ", "");
        }
        Claims claims = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }

    @Override
    public String parseTokenToId(String token) {
        if(token.startsWith("Bearer ")){
            token = token.replace("Bearer ", "");
        }
        Claims claims = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("id").toString();
    }

    @Override
    public String parseTokenToEmail(String token) {
        if(token.startsWith("Bearer ")){
            token = token.replace("Bearer ", "");
        }
        Claims claims = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("email").toString();
    }

}
