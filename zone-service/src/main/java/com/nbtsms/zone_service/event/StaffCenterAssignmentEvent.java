package com.nbtsms.zone_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffCenterAssignmentEvent {
    private UUID centerId;
    private UUID staffId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
