package com.nbts.management.donor_service.scheduler;

import com.nbts.management.donor_service.constants.KafkaTopics;
import com.nbts.management.donor_service.dto.AppointmentResponseDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.event.DonorAuthCreatedEvent;
import com.nbts.management.donor_service.event.DonorNotificationEvent;
import com.nbts.management.donor_service.service.impl.AppointmentServiceImpl;
import com.nbts.management.donor_service.service.impl.DonorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AppointmentNotificationScheduler {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentNotificationScheduler.class);

    private final AppointmentServiceImpl appointmentService;
    private final DonorServiceImpl donorService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AppointmentNotificationScheduler(AppointmentServiceImpl appointmentService, DonorServiceImpl donorService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.appointmentService = appointmentService;
        this.donorService = donorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Run daily at 8:00 AM (adjust cron as needed)
    @Scheduled(cron = "0 0 8 * * ?")
    public void notifyFiveDaysBeforeAppointments() {
        LocalDate targetDate = LocalDate.now().plusDays(5);
        List<AppointmentResponseDTO> appointments = appointmentService.findAppointmentsOnDate(targetDate);
        logger.info("{} appointments found", appointments.size());

        String link = "https://nbts.go.tz/centers/";

        appointments.forEach(appointmentResponseDTO -> kafkaTemplate.send(KafkaTopics.DONOR_NOTIFICATION, new DonorNotificationEvent(
                appointmentResponseDTO.getPhoneNumber(),
                "Kumbusho: Miadi yako ya kuchangia damu iko ndani ya siku 5. Tafadhali tembelea " + link + " kujua kituo chako.",
                link
        )));

    }

    // Run daily at 8:05 AM (just after the first job)
    @Scheduled(cron = "0 5 8 * * ?")
    public void notifyOneDayBeforeAppointments() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        List<AppointmentResponseDTO> appointments = appointmentService.findAppointmentsOnDate(targetDate);
        logger.info("{} appointments found for 1-day reminder", appointments.size());
        String link = "https://nbts.go.tz/centers/";

        appointments.forEach(appointmentResponseDTO -> kafkaTemplate.send(KafkaTopics.DONOR_NOTIFICATION, new DonorNotificationEvent(
                appointmentResponseDTO.getPhoneNumber(),
                "Kumbusho: Una miadi ya kuchangia damu kesho. Tembelea: " + link + " " + "kutazama vituo",
                link
        )));
    }

    // Debug: Run every 2 minutes
    @Scheduled(cron = "0 */2 * * * ?")
    public void debugEveryTwoMinutes() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        List<AppointmentResponseDTO> appointments = appointmentService.findAppointmentsOnDate(targetDate);

        logger.info("DEBUG: Scheduler is running every 2 minutes at {}, got {} appointments", LocalDate.now(), appointments.size());
        String link = "https://nbts.go.tz/centers/";
        /*
        appointments.forEach(appointmentResponseDTO -> kafkaTemplate.send(KafkaTopics.DONOR_NOTIFICATION, new DonorNotificationEvent(
                appointmentResponseDTO.getPhoneNumber(),
                "Kumbusho: Una miadi ya kuchangia damu kesho. Tembelea: " + link + " " + "kutazama vituo",
                link
        )));

         */
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void saveDonorAuth() {
        List<DonorResponseDTO> allByAuthSavedFalse = donorService.getAllByAuthSavedFalse();
        logger.info("{} records with no auth", allByAuthSavedFalse.size());

        allByAuthSavedFalse.forEach(donorResponseDTO -> kafkaTemplate.send(
                KafkaTopics.SAVE_DONOR_AUTH,
                new DonorAuthCreatedEvent(
                        donorResponseDTO.getId(),
                        donorResponseDTO.getPhoneNumber(),
                        false
                )
        ));
    }
}
