package com.paybridge.user.service.service.impl;

import com.paybridge.user.service.client.WalletClient;
import com.paybridge.user.service.common.response.ApiResponse;
import com.paybridge.user.service.dto.AuthResponse;
import com.paybridge.user.service.dto.LoginRequest;
import com.paybridge.user.service.dto.RegisterRequest;
import com.paybridge.user.service.dto.WalletCreateRequest;
import com.paybridge.user.service.entity.Role;
import com.paybridge.user.service.entity.User;
import com.paybridge.user.service.repository.RoleRepository;
import com.paybridge.user.service.repository.UserRepository;
import com.paybridge.user.service.security.AppUserDetailsService;
import com.paybridge.user.service.security.JwtService;
import com.paybridge.user.service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    @Autowired
    private WalletClient walletClient;

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

        // Resilient wallet creation (post-tx, fire-and-forget)
        createWalletForUser(user.getId().toString());

        return ApiResponse.success("Email registered successfully",null);
    }

    private void createWalletForUser(String userId) {
        try {
            WalletCreateRequest walletReq = new WalletCreateRequest();
            walletReq.setUserId(userId);
            walletReq.setCurrency("IDR");
            walletClient.createWallet(walletReq);
            log.info("Wallet creation initiated for user: {}", userId);
        } catch (Exception e) {
            log.error("Wallet creation failed for user {}: {}", userId, e.getMessage(), e);
            // Optional: Emit event for retry/notify (e.g., via Spring Events or Kafka)
            // Don't rethrow—registration succeeds
        }
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
