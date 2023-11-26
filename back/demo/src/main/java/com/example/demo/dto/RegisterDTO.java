package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class RegisterDTO {
    @Size(max = 255, min = 3)
    private String email;
    @Size(max = 255, min = 3)
    private String username;
    @Size(max = 255, min = 3)
    private String password;
    @Size(max = 255, min = 3)
    private String avatarUrl;
}
