package com.nbtsms.identity_service.client;

import com.nbtsms.identity_service.service.impl.InternalTokenService;
import com.nbtsms.identity_service.config.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.UUID;

@Component
public class ZoneServiceClient {

    private final RestTemplate restTemplate;
    private final InternalTokenService internalTokenService;

    @Autowired
    public ZoneServiceClient(RestTemplate restTemplate, InternalTokenService internalTokenService) {
        this.restTemplate = restTemplate;
        this.internalTokenService = internalTokenService;
    }

    public boolean zoneExists(UUID zoneId) {
        String url = ApiPaths.ZONE_EXISTS.replace("{zoneId}", zoneId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check if zone exists", e);
        }
    }

    public boolean centerExists(UUID centerId) {
        String url = ApiPaths.CENTER_EXISTS.replace("{centerId}", centerId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check if center exists", e);
        }
    }

    public UUID zoneId(UUID zoneId) {
        String url = ApiPaths.ZONE_ID.replace("{zoneId}", zoneId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<UUID> response = restTemplate.exchange(url, HttpMethod.GET, entity, UUID.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check if center exists", e);
        }
    }

    public boolean isCenterAssociatedWithZone(UUID zoneId, UUID centerId) {
        String url = ApiPaths.CENTER_ZONE_ASSOCIATION
                .replace("{zoneId}", zoneId.toString())
                .replace("{centerId}", centerId.toString());
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Zone or Center not found");
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check if center is associated with zone", e);
        }
    }


    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(internalTokenService.getToken());
        return headers;
    }
}
