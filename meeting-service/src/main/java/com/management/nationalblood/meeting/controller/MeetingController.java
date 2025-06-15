package com.management.nationalblood.meeting.controller;

import com.management.nationalblood.meeting.dto.CreateMeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingResponseDTO;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.service.impl.MeetingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("")
public class MeetingController {
    private final MeetingServiceImpl meetingService;

    public MeetingController(MeetingServiceImpl meetingService) {
        this.meetingService = meetingService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZER')")
    @PostMapping
    public ResponseEntity<MeetingResponseDTO<Map<String, Object>>> createMeeting(
            @Valid @RequestBody() CreateMeetingDTO createMeetingDTO,
            HttpServletRequest request
    ) {
        MeetingResponseDTO<Map<String, Object>> response = MeetingResponseDTO.ok(
                Map.of("meetingId", meetingService.createMeeting(createMeetingDTO)),
                "Meeting created successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("meeting/{meetingId}")
    public ResponseEntity<MeetingResponseDTO<MeetingDTO>> getMeeting(
            @PathVariable UUID meetingId,
            HttpServletRequest request
    ) {
        MeetingResponseDTO<MeetingDTO> response = MeetingResponseDTO.ok(
                meetingService.getMeeting(meetingId),
                "Meeting retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("organizer/{organizerId}/meetings")
    public ResponseEntity<MeetingResponseDTO<Page<MeetingDTO>>> getMeetingsByOrganizer(
            @PathVariable UUID organizerId,
            @RequestParam(required = false) MeetingStatus status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDateTime scheduledAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "scheduledAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<MeetingDTO> meetings = meetingService.getMeetingsByOrganizer(organizerId, status, location, scheduledAt, pageable);
        MeetingResponseDTO<Page<MeetingDTO>> response = MeetingResponseDTO.ok(
                meetings,
                "Meetings retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("staff/{staffId}/meetings")
    public ResponseEntity<MeetingResponseDTO<Page<MeetingDTO>>> getMeetingsByStaff(
            @PathVariable UUID staffId,
            @RequestParam(required = false) MeetingStatus status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDateTime scheduledAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "scheduledAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<MeetingDTO> meetings = meetingService.getMeetingsByStaff(staffId, status, location, scheduledAt, pageable);
        MeetingResponseDTO<Page<MeetingDTO>> response = MeetingResponseDTO.ok(
                meetings,
                "Meetings retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
