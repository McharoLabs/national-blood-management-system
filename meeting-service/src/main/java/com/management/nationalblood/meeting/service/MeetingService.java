package com.management.nationalblood.meeting.service;

import com.management.nationalblood.meeting.dto.CreateMeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingStaffAssignmentDTO;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.event.StaffAssignedEvent;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MeetingService {
    UUID createMeeting(CreateMeetingDTO createMeetingDTO) throws BadRequestException;
    MeetingDTO getMeeting(UUID meetingId) throws NotFoundException;
    void assignStaffToMeeting(UUID meetingId, MeetingStaffAssignmentDTO staffAssignmentDTO) throws NotFoundException, BadRequestException;
    void removeStaffFromMeeting(UUID meetingId, MeetingStaffAssignmentDTO staffAssignmentDTO) throws NotFoundException, BadRequestException;
    void assignStaffToMeetingEvent(StaffAssignedEvent event);

    void rescheduleMeeting(UUID meetingId, LocalDateTime newScheduledAt) throws NotFoundException, BadRequestException;
    void updateMeetingStatus(UUID meetingId, MeetingStatus status) throws NotFoundException, BadRequestException;

    Page<MeetingDTO> getMeetingsByOrganizer(
            UUID organizerId,
            MeetingStatus status,
            String location,
            LocalDateTime scheduledAt,
            Pageable pageable
    );

    Page<MeetingDTO> getMeetingsByStaff(
            UUID staffId,
            MeetingStatus status,
            String location,
            LocalDateTime scheduledAt,
            Pageable pageable
    );
}
