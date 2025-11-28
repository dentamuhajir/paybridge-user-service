package com.paybridge.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String fullName;
    @NotBlank(message = "is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "is required")
    private String phoneNumber;


}
