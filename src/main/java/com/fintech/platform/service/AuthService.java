package com.fintech.platform.service;

import com.fintech.platform.dto.LoginRequest;
import com.fintech.platform.dto.RegisterRequest;

public interface AuthService {
    public String register(RegisterRequest request);
    public Boolean login(LoginRequest request);
}
