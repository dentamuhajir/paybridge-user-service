package com.paybridge.user.service.client;

import com.paybridge.user.service.dto.WalletCreateRequest;  // Assume this DTO exists
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WalletClient {

    private final RestTemplate restTemplate;

    private final String walletEndpoint;

    private final String transactionToken;

    public WalletClient(RestTemplate restTemplate,
                        @Value("${TRANSACTION_SERVICE_ENDPOINT}/api/v1/wallet") String endpoint,
                        @Value("${TOKEN_TRANSACTION_SERVICE}") String token) {
        this.restTemplate = restTemplate;
        this.walletEndpoint = endpoint;
        this.transactionToken = token;
        log.info("WalletClient initialized: URL={}, Token length={}", walletEndpoint, transactionToken.length());
    }

    public void createWallet(WalletCreateRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + transactionToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<WalletCreateRequest> entity = new HttpEntity<>(request, headers);

            restTemplate.postForObject(walletEndpoint , entity, Void.class);
            log.info("Wallet creation request sent for user: {}", request.getUserId());
        } catch (Exception e) {
            log.error("Failed to create wallet for user {}: {}", request.getUserId(), e.getMessage());
            throw new RuntimeException("Wallet creation failed", e);  // Or handle gracefully
        }
    }
}