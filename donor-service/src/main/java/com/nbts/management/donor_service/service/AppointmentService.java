package com.nbts.management.donor_service.service;

import com.nbts.management.donor_service.dto.AppointmentResponseDTO;
import com.nbts.management.donor_service.dto.CreateAppointmentDTO;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    void createAppointment(CreateAppointmentDTO createAppointmentDTO) throws BadRequestException;

    AppointmentResponseDTO appointment(UUID appointmentId) throws NotFoundException;
    List<AppointmentResponseDTO> findAppointmentsOnDate(LocalDate date);

    Page<AppointmentResponseDTO> findAppointmentsByOptionalDateRangeAndDonorName(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String fullName,
            Pageable pageable);

    Page<AppointmentResponseDTO> findAppointmentsByDonorIdAndOptionalDateAndName(
            UUID donorId,
            LocalDateTime appointmentDate,
            String fullName,
            Pageable pageable);
}
