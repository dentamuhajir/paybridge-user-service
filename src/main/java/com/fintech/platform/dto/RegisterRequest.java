package com.fintech.platform.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;

}
