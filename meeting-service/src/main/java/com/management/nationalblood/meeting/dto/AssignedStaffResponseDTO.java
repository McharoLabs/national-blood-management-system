package com.management.nationalblood.meeting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AssignedStaffResponseDTO {
    private UUID id;
    private UUID staffId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UUID meetingId;
}
