package com.nbts.management.donor_service.entity;

import com.nbts.management.donor_service.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Donor donor;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static boolean isAtLeastThreeMonthsApart(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2) {
        return appointmentDate1.isBefore(appointmentDate2.minusMonths(3))
                || appointmentDate1.isAfter(appointmentDate2.plusMonths(3));
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
