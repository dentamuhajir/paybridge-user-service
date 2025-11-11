package com.fintech.platform.controller;

import com.fintech.platform.dto.LoginRequest;
import com.fintech.platform.dto.RegisterRequest;
import com.fintech.platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterRequest request) {
        String resp = authService.register(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest request) {
        Boolean verify = authService.login(request);
        if(verify == false) {
            return ResponseEntity
                    .badRequest()
                    .body("Login is failed");
        }
        return ResponseEntity.ok("Login is success");
    }
}
