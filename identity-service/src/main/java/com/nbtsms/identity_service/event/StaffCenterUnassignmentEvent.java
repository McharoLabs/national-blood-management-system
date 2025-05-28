package com.nbtsms.identity_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffCenterUnassignmentEvent {
    private UUID centerId;
    private UUID staffId;
}
