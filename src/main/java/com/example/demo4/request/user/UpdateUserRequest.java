package com.example.demo4.request.user;

import com.example.demo4.contant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String id;
    private String avatar;
    private String username;
    private int age;
    private UserStatus userStatus;
}
