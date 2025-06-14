package com.nbts.management.donor_service.mapper;

import com.nbts.management.donor_service.dto.CreateAppointmentDTO;
import com.nbts.management.donor_service.dto.AppointmentResponseDTO;
import com.nbts.management.donor_service.entity.Appointment;
import com.nbts.management.donor_service.enums.AppointmentStatus;

public class AppointmentMapper {
    public static Appointment toEntity(CreateAppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setNotes(dto.getNotes());
        return appointment;
    }

    public static AppointmentResponseDTO toResponseDto(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setDonorId(appointment.getDonor().getId());
        dto.setDonorName(appointment.getDonor().getFullName());
        dto.setPhoneNumber(appointment.getDonor().getPhoneNumber());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());
        dto.setCreatedAt(appointment.getCreatedAt());
        return dto;
    }
}
