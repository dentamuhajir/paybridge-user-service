package com.paybridge.user.service.controller;

import com.paybridge.user.service.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<?> checkDatabaseConnection() {
        logger.info("Health check: checking database connection...");
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(5)) {
                logger.info("Database connection is valid");
                return ResponseEntity.ok(
                        ApiResponse.success("Database connection is valid!", null)
                );
            } else {
                logger.info("Database connection is failed");
                return ResponseEntity.status(500).body(
                        ApiResponse.error("Database connection failed", 500)
                );
            }

        } catch (Exception e) {
            logger.error("Health check error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    ApiResponse.error("Error: " + e.getMessage(), 500)
            );
        }
    }
}
