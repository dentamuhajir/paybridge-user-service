package com.paybridge.user.service.controller;

import com.paybridge.user.service.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        logger.info("Health check: checking DB & Redis...");

        // =======================
        // 1. Check Database
        // =======================
        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(5)) {
                logger.error("Database connection FAILED");
                return ResponseEntity.status(500).body(
                        ApiResponse.error("Database connection failed", 500)
                );
            }
        } catch (Exception e) {
            logger.error("Database health check error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    ApiResponse.error("Database error: " + e.getMessage(), 500)
            );
        }

        // =======================
        // 2. Check Redis
        // =======================
        try {
            String pong = redisTemplate.getConnectionFactory()
                    .getConnection()
                    .ping();

            if (!"PONG".equalsIgnoreCase(pong)) {
                logger.error("Redis PING returned: {}", pong);
                return ResponseEntity.status(500).body(
                        ApiResponse.error("Redis connection failed", 500)
                );
            }

        } catch (Exception e) {
            logger.error("Redis health check error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    ApiResponse.error("Redis error: " + e.getMessage(), 500)
            );
        }

        // =======================
        // SUCCESS
        // =======================
        logger.info("Health check passed: DB and Redis OK");
        return ResponseEntity.ok(
                ApiResponse.success("DB and Redis connection are healthy!", null)
        );
    }
}
