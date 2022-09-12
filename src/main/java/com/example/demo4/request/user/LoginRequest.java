package com.example.demo4.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(regexp = ".+@ntq-solution.com.vn")
    @Indexed(unique = true)
    private String email;
    @Size(min = 8,max = 20,message = "Password is less than 8 or greater than 20")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",message = "Password Not Valid")
    private String password;
}
