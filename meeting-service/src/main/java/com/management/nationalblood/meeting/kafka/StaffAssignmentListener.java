package com.management.nationalblood.meeting.kafka;

import com.management.nationalblood.meeting.constants.KafkaTopics;
import com.management.nationalblood.meeting.event.StaffAssignedEvent;
import com.management.nationalblood.meeting.service.impl.MeetingServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StaffAssignmentListener {
    private final MeetingServiceImpl meetingService;

    public StaffAssignmentListener(MeetingServiceImpl meetingService) {
        this.meetingService = meetingService;
    }

    @KafkaListener(topics = KafkaTopics.STAFF_MEETING_ASSIGNMENT_RES)
    public void handleCreateDonorAuth(StaffAssignedEvent event) {
        meetingService.assignStaffToMeetingEvent(event);
    }
}
