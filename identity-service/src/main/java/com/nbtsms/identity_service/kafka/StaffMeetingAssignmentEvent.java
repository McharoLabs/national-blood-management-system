package com.nbtsms.identity_service.kafka;

import com.nbtsms.identity_service.constant.KafkaTopics;
import com.nbtsms.identity_service.event.StaffAssignedEvent;
import com.nbtsms.identity_service.service.impl.UserServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StaffMeetingAssignmentEvent {
    private final UserServiceImpl userService;

    public StaffMeetingAssignmentEvent(UserServiceImpl userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = KafkaTopics.STAFF_MEETING_ASSIGNMENT)
    public void handleCreateDonorAuth(StaffAssignedEvent event) {
        userService.assignedStaffToMeeting(event);
    }
}
