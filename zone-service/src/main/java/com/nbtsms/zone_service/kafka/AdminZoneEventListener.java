package com.nbtsms.zone_service.kafka;

import com.nbtsms.zone_service.constant.KafkaTopics;
import com.nbtsms.zone_service.event.AdminZoneAssignmentEvent;
import com.nbtsms.zone_service.event.AdminZoneUnassignmentEvent;
import com.nbtsms.zone_service.service.impl.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AdminZoneEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AdminZoneEventListener.class);
    private final AdminServiceImpl adminService;

    public AdminZoneEventListener(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @KafkaListener(topics = KafkaTopics.ADMIN_ZONE_ASSIGNMENT)
    public void handleAdminZoneAssignment(AdminZoneAssignmentEvent event) {
        logger.info("Received AdminZoneAssignmentEvent: {}", event);
        adminService.addAdminToZone(event);
    }

    @KafkaListener(topics = KafkaTopics.ADMIN_ZONE_UNASSIGNMENT)
    public void handleAdminZoneUnassignment(AdminZoneUnassignmentEvent event) {
        logger.info("Received AdminZoneUnassignmentEvent: {}", event);
        adminService.removeAdminFromZone(event);
    }
}
