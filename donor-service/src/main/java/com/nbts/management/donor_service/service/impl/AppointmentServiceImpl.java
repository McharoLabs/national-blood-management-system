package com.nbts.management.donor_service.service.impl;

import com.nbts.management.donor_service.dto.AppointmentResponseDTO;
import com.nbts.management.donor_service.dto.CreateAppointmentDTO;
import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.entity.Appointment;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.NotFoundException;
import com.nbts.management.donor_service.mapper.AppointmentMapper;
import com.nbts.management.donor_service.mapper.DonorMapper;
import com.nbts.management.donor_service.repository.AppointmentRepository;
import com.nbts.management.donor_service.repository.DonorRepository;
import com.nbts.management.donor_service.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    private final AppointmentRepository appointmentRepository;
    private final DonorRepository donorRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DonorRepository donorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.donorRepository = donorRepository;
    }

    @Override
    public void createAppointment(CreateAppointmentDTO createAppointmentDTO) throws BadRequestException {
        String phoneNumber = createAppointmentDTO.getPhoneNumber();
        String lastNine = phoneNumber.length() >= 9
                ? phoneNumber.substring(phoneNumber.length() - 9)
                : phoneNumber;


        Donor donor = donorRepository.findByLastNinePhoneDigits(lastNine)
                .orElse(null);

        if (donor == null) {
            CreateDonorDTO createDonorDTO = new CreateDonorDTO();
            createDonorDTO.setPhoneNumber(phoneNumber);
            createDonorDTO.setFullName(createAppointmentDTO.getFullName());
            donor = DonorMapper.toEntity(createDonorDTO);
            donorRepository.save(donor);
            logger.info("New donor created with phone number: {}", phoneNumber);
        }

        Appointment appointment = AppointmentMapper.toEntity(createAppointmentDTO);


        boolean hasRecentAppointment = donor.getAppointments().stream()
                .anyMatch(existingAppointment ->
                        !Appointment.isAtLeastThreeMonthsApart(appointment.getAppointmentDate(), existingAppointment.getAppointmentDate())
                );

        if (hasRecentAppointment) {
            throw new BadRequestException(Map.of("detail", "You already have an appointment within the last 3 months"));
        }


        appointment.setDonor(donor);
        donor.getAppointments().add(appointment);

        donorRepository.save(donor);

        Appointment savedAppointment = donor.getAppointments()
                        .stream()
                                .filter(a -> a.getAppointmentDate().equals(createAppointmentDTO.getAppointmentDate()))
                                        .findFirst()
                                                .orElseThrow(() -> new BadRequestException(Map.of("detail", "Appointment not saved successfully")));

        logger.info("Appointment on {} saved successfully for donor ID: {}", savedAppointment.getAppointmentDate(), donor.getId());
    }


    @Override
    public AppointmentResponseDTO appointment(UUID appointmentId) throws NotFoundException {
        return AppointmentMapper.toResponseDto(appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Appointment not found"))));
    }

    @Override
    public List<AppointmentResponseDTO> findAppointmentsOnDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return appointmentRepository.findAppointmentsBetweenDates(startOfDay, endOfDay)
                .stream()
                .map(AppointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AppointmentResponseDTO> findAppointmentsByOptionalDateRangeAndDonorName(LocalDateTime startDate, LocalDateTime endDate, String fullName, Pageable pageable) {
        return appointmentRepository.findAppointmentsByOptionalDonorNameAndAppointmentDateRange(startDate, endDate, fullName, pageable)
                .map(AppointmentMapper::toResponseDto);
    }

    @Override
    public Page<AppointmentResponseDTO> findAppointmentsByDonorIdAndOptionalDateAndName(UUID donorId, LocalDateTime appointmentDate, String fullName, Pageable pageable) {
        return appointmentRepository.findAppointmentsByOptionalDonorIdAndDonorNameAndAppointmentDate(appointmentDate, donorId, fullName, pageable)
                .map(AppointmentMapper::toResponseDto);
    }
}
