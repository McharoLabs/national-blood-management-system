package com.nbtsms.zone_service.client;

import com.nbtsms.zone_service.config.ApiPaths;
import com.nbtsms.zone_service.service.impl.InternalTokenService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class IdentityServiceClient {
    private final RestTemplate restTemplate;
    private final InternalTokenService internalTokenService;

    public IdentityServiceClient(RestTemplate restTemplate, InternalTokenService internalTokenService) {
        this.restTemplate = restTemplate;
        this.internalTokenService = internalTokenService;
    }

    public boolean staffExists(UUID staffId) {
        String url = ApiPaths.STAFF_EXISTS.replace("{staffId}", staffId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check if staff exists", e);
        }
    }

    public UUID staffZoneId(UUID staffId) {
        String url = ApiPaths.STAFF_ZONE_ID.replace("{staffId}", staffId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String body = response.getBody();
            if (body == null || body.isBlank()) {
                throw new RuntimeException("Empty response body when retrieving staff zone id");
            }

            return UUID.fromString(body);

        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to get staff zone id", ex);
        }
    }


    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(internalTokenService.getToken());
        return headers;
    }
}
