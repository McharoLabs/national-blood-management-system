package com.management.nationalblood.notificationservice.kafka;

import com.management.nationalblood.notificationservice.constant.KafkaTopics;
import com.management.nationalblood.notificationservice.event.DonorNotificationEvent;
import com.management.nationalblood.notificationservice.service.impl.NotificationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {
    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);
    private final NotificationServiceImpl notificationService;

    public NotificationEventListener(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = KafkaTopics.DONOR_NOTIFICATION)
    public void handleAdminZoneAssignment(DonorNotificationEvent event) {
        logger.info("Received donor event: {}", event);
        notificationService.appointmentNotification(event);
    }
}
