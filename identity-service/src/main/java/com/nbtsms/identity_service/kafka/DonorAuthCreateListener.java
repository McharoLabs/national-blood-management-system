package com.nbtsms.identity_service.kafka;

import com.nbtsms.identity_service.constant.KafkaTopics;
import com.nbtsms.identity_service.event.DonorAuthCreatedEvent;
import com.nbtsms.identity_service.service.impl.DonorAuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DonorAuthCreateListener {
    private static final Logger logger = LoggerFactory.getLogger(DonorAuthCreateListener.class);
    private final DonorAuthServiceImpl donorService;

    public DonorAuthCreateListener(DonorAuthServiceImpl donorService) {
        this.donorService = donorService;
    }

    @KafkaListener(topics = KafkaTopics.SAVE_DONOR_AUTH)
    public void handleCreateDonorAuth(DonorAuthCreatedEvent event) {
        logger.info("Received donor event: {}", event);
        donorService.createDonorAuth(event);
    }
}
