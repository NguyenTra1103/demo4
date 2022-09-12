package com.example.demo4.domain;

import com.example.demo4.contant.Role;
import com.example.demo4.contant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    private String id;
    private String avatarId;
    private String username;
    private int age;
    private String password;
    private String email;
    private Role role;
    private UserStatus userStatus;
    public static final String KEY ="${myntq.secret-key}";
}
