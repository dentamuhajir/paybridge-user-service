package com.fintech.platform.service;

import com.fintech.platform.common.response.ApiResponse;
import com.fintech.platform.dto.LoginRequest;
import com.fintech.platform.dto.RegisterRequest;

public interface AuthService {
    public ApiResponse register(RegisterRequest request);
    public Boolean login(LoginRequest request);
}
