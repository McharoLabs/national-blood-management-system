package com.nbts.management.donor_service.dto;

import com.nbts.management.donor_service.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private UUID id;
    private UUID donorId;
    private String phoneNumber;
    private String donorName;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private String notes;
    private LocalDateTime createdAt;
}
