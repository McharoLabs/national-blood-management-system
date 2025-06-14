package com.management.nationalblood.notificationservice.service.impl;

import com.management.nationalblood.notificationservice.config.SmsProperties;
import com.management.nationalblood.notificationservice.event.DonorNotificationEvent;
import com.management.nationalblood.notificationservice.service.NotificationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final RestTemplate restTemplate;
    private final SmsProperties smsProperties;

    public NotificationServiceImpl(RestTemplate restTemplate, SmsProperties smsProperties) {
        this.restTemplate = restTemplate;
        this.smsProperties = smsProperties;
    }

    @Override
    @Transactional
    public void appointmentNotification(DonorNotificationEvent event) {
        String url = smsProperties.getUrl();

        // Prepare Basic Auth header
        String authStr = smsProperties.getUsername() + ":" + smsProperties.getPassword();
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + base64Creds);

        // Build payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("source_addr", smsProperties.getSource());
        payload.put("schedule_time", "");
        payload.put("encoding", "0");
        payload.put("message", event.getMessage());

        List<Map<String, Object>> recipients = new ArrayList<>();
        Map<String, Object> recipient = new HashMap<>();
        recipient.put("recipient_id", 1);
        recipient.put("dest_addr", event.getPhoneNumber());
        recipients.add(recipient);

        payload.put("recipients", recipients);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            logger.info("SMS sent successfully, response: {}", response.getBody());
        } catch (Exception e) {
            logger.error("Failed to send SMS", e);
        }
    }

}
