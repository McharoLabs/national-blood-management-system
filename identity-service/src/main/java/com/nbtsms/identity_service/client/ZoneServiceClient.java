package com.nbtsms.identity_service.client;

import com.management.nationalblood.shared.dto.CenterResponseDTO;
import com.management.nationalblood.shared.dto.ZoneResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.UUID;

@Component
public class ZoneServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ZoneServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean zoneExists(UUID zoneId) {
        String url = "http://zone-service/api/v1/zones/" + zoneId + "/exists";
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public boolean centerExists(UUID centerId) {
        String url = "http://zone-service/api/v1/centers/" + centerId + "/exists";
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public CenterResponseDTO fetchCenter(UUID centerId) {
        String url = "http://zone-service/api/v1/centers/" + centerId + "/center";
        try {
            ResponseEntity<CenterResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    CenterResponseDTO.class
            );
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            String responseBody = e.getResponseBodyAsString();
            System.err.println("Zone not found: " + responseBody);
            throw new RuntimeException("Zone not found");

        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error occurred: " + e.getMessage());

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch zone", e);
        }
    }

    public ZoneResponseDTO fetchZone(UUID zoneId) {
        String url = "http://zone-service/api/v1/zones/" + zoneId + "/zone";
        try {
            ResponseEntity<ZoneResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ZoneResponseDTO.class
            );
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            String responseBody = e.getResponseBodyAsString();
            System.err.println("Zone not found: " + responseBody);
            throw new RuntimeException("Zone not found");

        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error occurred: " + e.getMessage());

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch zone", e);
        }
    }

}
