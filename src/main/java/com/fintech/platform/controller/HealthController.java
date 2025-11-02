package com.fintech.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {
    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public String checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection()){
            if(conn.isValid(5)) {
                return "Database connection is valid!";
            } else {
                return "Database connection failed";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
