package com.nbtsms.zone_service.kafka;

import com.nbtsms.zone_service.constant.KafkaTopics;
import com.nbtsms.zone_service.event.AdminZoneAssignmentEvent;
import com.nbtsms.zone_service.event.AdminZoneUnassignmentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AdminZoneEventListener {
    @KafkaListener(
            topics = KafkaTopics.ADMIN_ZONE_ASSIGNMENT
    )
    public void handleAdminZoneAssignment(AdminZoneAssignmentEvent event) {
        System.out.println("✅ Received AdminZoneAssignmentEvent: " + event);
        // Add business logic here
    }

    @KafkaListener(
            topics = KafkaTopics.ADMIN_ZONE_UNASSIGNMENT
    )
    public void handleAdminZoneUnassignment(AdminZoneUnassignmentEvent event) {
        System.out.println("✅ Received AdminZoneUnassignmentEvent: " + event);
        // Add business logic here
    }
}
