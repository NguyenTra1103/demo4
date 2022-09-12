package com.example.demo4.request.user;

import com.example.demo4.contant.Role;
import com.example.demo4.contant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String id;
    private MultipartFile avatar;
    private String username;
    @Email(regexp = ".+@ntq-solution.com.vn")
    @Indexed(unique = true)
    private String email;
    @Size(min = 8, max = 20, message = "Password is less than 8 or greater than 20")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",message = "Password not valid")
    private String password;
    private int age;
    private Status status;
    private Role role;
    private String key;
}
