package com.fintech.platform.service.impl;

import com.fintech.platform.dto.RegisterRequest;
import com.fintech.platform.entity.User;
import com.fintech.platform.repository.AuthRepository;
import com.fintech.platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;
    public String register(RegisterRequest request){
        if(authRepository.existsByEmail(request.getEmail())) {
            return "email has already registered";
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        authRepository.save(user);
        return "email is registered";
    }
}
