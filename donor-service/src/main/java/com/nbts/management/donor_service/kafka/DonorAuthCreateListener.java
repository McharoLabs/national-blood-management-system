package com.nbts.management.donor_service.kafka;

import com.nbts.management.donor_service.constants.KafkaTopics;
import com.nbts.management.donor_service.event.DonorAuthCreatedEvent;
import com.nbts.management.donor_service.service.impl.DonorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DonorAuthCreateListener {
    private static final Logger logger = LoggerFactory.getLogger(DonorAuthCreateListener.class);
    private final DonorServiceImpl donorService;

    public DonorAuthCreateListener(DonorServiceImpl donorService) {
        this.donorService = donorService;
    }

    @KafkaListener(topics = KafkaTopics.SAVE_DONOR_AUTH)
    public void handleAdminZoneAssignment(DonorAuthCreatedEvent event) {
        logger.info("Received donor event: {}", event);
        donorService.setDonorAuthSavedTrue(event);
    }
}
