package com.fintech.platform.service.impl;

import com.fintech.platform.common.response.ApiResponse;
import com.fintech.platform.dto.LoginRequest;
import com.fintech.platform.dto.RegisterRequest;
import com.fintech.platform.entity.Role;
import com.fintech.platform.entity.User;
import com.fintech.platform.repository.RoleRepository;
import com.fintech.platform.repository.UserRepository;
import com.fintech.platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ApiResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error("Email has already registered", 409);
        }

        Role userRole = roleRepository.findByName("USER");

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(userRole);
        userRepository.save(user);

        return ApiResponse.success("Email registered successfully",null);
    }

    public Boolean login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null) {
            System.out.println("error here");
            return  false;
        }

        boolean verify = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println(verify);
        return verify;
    }
}
