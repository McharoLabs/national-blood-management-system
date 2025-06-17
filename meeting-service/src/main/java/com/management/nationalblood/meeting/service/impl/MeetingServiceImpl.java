package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.constants.KafkaTopics;
import com.management.nationalblood.meeting.dto.CreateMeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingStaffAssignmentDTO;
import com.management.nationalblood.meeting.entity.AssignedStaff;
import com.management.nationalblood.meeting.entity.Meeting;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import com.management.nationalblood.meeting.event.StaffAssignedEvent;
import com.management.nationalblood.meeting.mapper.MeetingMapper;
import com.management.nationalblood.meeting.mapper.StaffAssignmentMapper;
import com.management.nationalblood.meeting.repository.MeetingRepository;
import com.management.nationalblood.meeting.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class MeetingServiceImpl implements MeetingService {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);
    private final MeetingRepository meetingRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MeetingServiceImpl(MeetingRepository meetingRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.meetingRepository = meetingRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public UUID createMeeting(CreateMeetingDTO createMeetingDTO) throws BadRequestException {
        Optional<Meeting> existingMeeting = meetingRepository.findByScheduledAtAndCenterIdAndLocation(
                createMeetingDTO.getScheduledAt(),
                createMeetingDTO.getCenterId(),
                createMeetingDTO.getLocation()
        );

        if (existingMeeting.isPresent()) {
            throw new BadRequestException(Map.of(
                    "scheduledAt", "Meeting with the same schedule and location already exists."
            ));
        }

        Meeting meeting = MeetingMapper.toEntity(createMeetingDTO);
        meetingRepository.save(meeting);
        logger.info("Created meeting with ID: {}", meeting.getId());
        return meeting.getId();

    }

    @Override
    public MeetingDTO getMeeting(UUID meetingId) throws NotFoundException {
        return MeetingMapper.toResponse(
                meetingRepository.findById(meetingId)
                        .orElseThrow(() -> new NotFoundException(Map.of(
                                "detail", "Meeting not found"
                        )))
        );
    }

    @Override
    public void assignStaffToMeeting(UUID meetingId, MeetingStaffAssignmentDTO staffAssignmentDTO) throws NotFoundException, BadRequestException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Meeting not found")));

        List<UUID> staffIds = staffAssignmentDTO.getStaffIds();
        if (staffIds == null || staffIds.isEmpty()) {
            throw new BadRequestException(Map.of("staffIds", "Staff list cannot be empty"));
        }

        List<Meeting> conflictingMeetings = meetingRepository.findMeetingsByScheduledAtAndStaffIdsExcludingMeeting(
                meeting.getScheduledAt(),
                staffIds,
                meetingId
        );

        if (!conflictingMeetings.isEmpty()) {
            throw new BadRequestException(Map.of("detail", "One or more staff members are already assigned to a meeting at the same schedule"));
        }

        staffIds.forEach(staffId -> {
            StaffAssignedEvent event = new StaffAssignedEvent();
            event.setStaffId(staffId);
            event.setMeetingId(meetingId);

            kafkaTemplate.send(KafkaTopics.STAFF_MEETING_ASSIGNMENT, event);
        });
    }


    @Override
    public void removeStaffFromMeeting(UUID meetingId, MeetingStaffAssignmentDTO staffAssignmentDTO)
            throws NotFoundException, BadRequestException {

        logger.info("Request to remove staff from meeting ID: {}", meetingId);

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> {
                    logger.warn("Meeting not found: {}", meetingId);
                    return new NotFoundException(Map.of("detail", "Meeting not found"));
                });

        List<UUID> staffIdsToRemove = staffAssignmentDTO.getStaffIds();

        if (meeting.getStaffs().isEmpty()) {
            logger.warn("Meeting {} has no assigned staff", meetingId);
            throw new BadRequestException(Map.of("detail", "No staff are currently assigned to this meeting"));
        }

        int beforeSize = meeting.getStaffs().size();

        meeting.getStaffs().removeIf(staff -> staffIdsToRemove.contains(staff.getStaffId()));

        int afterSize = meeting.getStaffs().size();
        int removedCount = beforeSize - afterSize;

        if (removedCount == 0) {
            logger.warn("No matching staff found to remove in meeting {}", meetingId);
            throw new BadRequestException(Map.of("detail", "No matching staff found for removal"));
        }

        meetingRepository.save(meeting);

        logger.info("Removed {} staff from meeting {}", removedCount, meetingId);
    }

    @Override
    public void assignStaffToMeetingEvent(StaffAssignedEvent event) {
        Meeting meeting = meetingRepository.findById(event.getMeetingId()).orElse(null);

        if (meeting == null) {
            logger.warn("Meeting not found: {}", event.getMeetingId());
            return;
        }

        boolean alreadyAssigned = meeting.getStaffs().stream()
                .anyMatch(s -> s.getStaffId().equals(event.getStaffId()));
        if (alreadyAssigned) return;

        AssignedStaff staff = StaffAssignmentMapper.toEntity(event);
        staff.setMeeting(meeting);
        meeting.getStaffs().add(staff);
        meetingRepository.save(meeting);

        logger.info("Staff {} assigned to meeting {}", staff.getStaffId(), meeting.getId());
    }


    @Override
    public void rescheduleMeeting(UUID meetingId, LocalDateTime newScheduledAt) throws NotFoundException, BadRequestException {
        logger.info("Attempting to reschedule meeting with ID {} to {}", meetingId, newScheduledAt);

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> {
                    logger.error("Meeting with ID {} not found", meetingId);
                    return new NotFoundException(Map.of("detail", "Meeting not found"));
                });

        if (meeting.getScheduledAt().equals(newScheduledAt)) {
            logger.warn("Meeting with ID {} already scheduled at {}", meetingId, newScheduledAt);
            throw new BadRequestException(Map.of("detail", "New schedule must be different from the current one"));
        }

        meeting.setScheduledAt(newScheduledAt);
        meetingRepository.save(meeting);
        logger.info("Meeting with ID {} successfully rescheduled to {}", meetingId, newScheduledAt);
    }


    @Override
    public void updateMeetingStatus(UUID meetingId, MeetingStatus status) throws NotFoundException, BadRequestException {
        logger.info("Updating status of meeting {} to {}", meetingId, status);

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> {
                    logger.error("Meeting with ID {} not found", meetingId);
                    return new NotFoundException(Map.of("detail", "Meeting not found"));
                });

        if (status == MeetingStatus.PLANNED) {
            logger.warn("Invalid status {} provided for meeting {}", status, meetingId);
            throw new BadRequestException(Map.of("detail", "Can not change to " + status + " From " + meeting.getStatus()));
        }

        if (status == MeetingStatus.COMPLETED && meeting.getScheduledAt().isAfter(LocalDateTime.now())) {
            logger.warn("Attempted to complete meeting {} before it occurred", meetingId);
            throw new BadRequestException(Map.of("detail", "Cannot complete a meeting that hasn’t occurred yet"));
        }

        if (meeting.getStatus() == MeetingStatus.COMPLETED && status == MeetingStatus.CANCELLED) {
            logger.warn("Attempted to cancel already completed meeting {}", meetingId);
            throw new BadRequestException(Map.of("detail", "Cannot cancel a completed meeting"));
        }

        meeting.setStatus(status);
        meetingRepository.save(meeting);
        logger.info("Meeting with ID {} updated to status {}", meetingId, status);
    }


    @Override
    public Page<MeetingDTO> getMeetingsByOrganizer(UUID organizerId, MeetingStatus status, String location, LocalDateTime scheduledAt, Pageable pageable) {
        return meetingRepository.findByOrganizerFiltered(
                organizerId, status, location, scheduledAt, pageable)
                .map(MeetingMapper::toResponse);
    }

    @Override
    public Page<MeetingDTO> getMeetingsByStaff(UUID staffId, MeetingStatus status, String location, LocalDateTime scheduledAt, Pageable pageable) {
        return meetingRepository.findByStaffFiltered(
                staffId, status, location, scheduledAt, pageable)
                .map(MeetingMapper::toResponse);
    }
}
