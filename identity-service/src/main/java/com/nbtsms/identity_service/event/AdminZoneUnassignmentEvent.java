package com.nbtsms.identity_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminZoneUnassignmentEvent {
    private UUID zoneId;
    private UUID adminId;
}
