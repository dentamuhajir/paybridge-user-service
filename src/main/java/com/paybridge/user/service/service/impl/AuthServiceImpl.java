package com.paybridge.user.service.service.impl;

import com.paybridge.user.service.common.response.ApiResponse;
import com.paybridge.user.service.dto.AuthResponse;
import com.paybridge.user.service.dto.LoginRequest;
import com.paybridge.user.service.dto.RegisterRequest;
import com.paybridge.user.service.entity.Role;
import com.paybridge.user.service.entity.User;
import com.paybridge.user.service.repository.RoleRepository;
import com.paybridge.user.service.repository.UserRepository;
import com.paybridge.user.service.security.AppUserDetailsService;
import com.paybridge.user.service.security.JwtService;
import com.paybridge.user.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private AppUserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    public ApiResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error("Email has already registered", 409);
        }

        Role userRole = roleRepository.findByName("USER");

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(userRole);
        userRepository.save(user);

        return ApiResponse.success("Email registered successfully",null);
    }

    public ApiResponse login(LoginRequest request) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .build();

        return ApiResponse.success("Token generated", authResponse);
    }
}
