package com.paybridge.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WalletCreateRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String currency;
}
