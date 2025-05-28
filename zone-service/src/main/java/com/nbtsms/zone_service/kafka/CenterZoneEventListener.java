package com.nbtsms.zone_service.kafka;

import com.nbtsms.zone_service.constant.KafkaTopics;
import com.nbtsms.zone_service.event.StaffCenterAssignmentEvent;
import com.nbtsms.zone_service.event.StaffCenterUnassignmentEvent;
import com.nbtsms.zone_service.service.impl.CenterStaffServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CenterZoneEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CenterZoneEventListener.class);
    private final CenterStaffServiceImpl centerService;

    public CenterZoneEventListener(CenterStaffServiceImpl centerService) {
        this.centerService = centerService;
    }

    @KafkaListener(topics = KafkaTopics.STAFF_CENTER_ASSIGNMENT)
    public void handleStaffCenterAssignment(StaffCenterAssignmentEvent event) {
        logger.info("Received StaffCenterAssignmentEvent: {}", event);
        centerService.addStaffToCenter(event);
    }

    @KafkaListener(topics = KafkaTopics.STAFF_CENTER_UNASSIGNMENT)
    public void handleStaffCenterUnassignment(StaffCenterUnassignmentEvent event) {
        logger.info("Received StaffCenterUnassignmentEvent: {}", event);
        centerService.removeStaffFromCenter(event);
    }
}
