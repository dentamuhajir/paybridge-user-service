package com.fintech.platform.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    String token;
}
