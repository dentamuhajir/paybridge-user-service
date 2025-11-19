package com.fintech.platform.controller;

import com.fintech.platform.common.response.ApiResponse;
import com.fintech.platform.dto.LoginRequest;
import com.fintech.platform.dto.RegisterRequest;
import com.fintech.platform.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.info("Received register request for email: {}", request.getEmail());
        ApiResponse resp = authService.register(request);
        logger.info("Register result: status={}, success={}", resp.getStatus(), resp.isSuccess());
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse resp = authService.login(request);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
