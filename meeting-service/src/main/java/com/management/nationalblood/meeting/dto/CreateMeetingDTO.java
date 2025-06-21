package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.MeetingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMeetingDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Scheduled date and time is required")
    @Future(message = "Scheduled date and time must be in the future")
    private LocalDateTime scheduledAt;

    @NotNull(message = "Center ID is required")
    private UUID centerId;

    @NotNull(message = "Organizer ID is required")
    private UUID organizerId;

    private MeetingStatus status;

    @Size(max = 1000, message = "Remarks must not exceed 1000 characters")
    private String remarks;

    @NotBlank(message = "Meeting location is mandatory")
    private String location;
}
