package com.management.nationalblood.laboratoryservice.entity;

import com.management.nationalblood.laboratoryservice.enums.ABOGroup;
import com.management.nationalblood.laboratoryservice.enums.RhFactor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "lab_test_results")
public class LabTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Assessment assessment;

    @Column(nullable = false)
    private boolean hivTestResult;

    @Column(nullable = false)
    private boolean hepatitisBTestResult;

    @Column(nullable = false)
    private boolean hepatitisCTestResult;

    @Column(nullable = false)
    private boolean syphilisTestResult;

    @Column(nullable = false)
    private boolean malariaTestResult;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ABOGroup aboGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RhFactor rhFactor;

    private String labTechnicianName;

    private String notes;

    @Column(nullable = false)
    private LocalDateTime testedAt;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    private boolean submittedToDonor = false;

    @PrePersist
    public void prePersist() {
        recordedAt = LocalDateTime.now();
    }
}
