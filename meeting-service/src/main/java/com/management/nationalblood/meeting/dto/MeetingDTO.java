package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private String location;
    private UUID centerId;
    private UUID organizerId;
    private List<AssignedStaffResponseDTO> staffs;
    private MeetingStatus status;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}