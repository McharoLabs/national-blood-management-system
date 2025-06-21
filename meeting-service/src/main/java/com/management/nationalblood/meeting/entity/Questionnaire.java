package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.FormProgress;
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
@Table(name = "questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Donor donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Meeting meeting;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PreliminaryQuestionnaire preliminaryQuestionnaire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private PhysicalExamination physicalExamination;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private HaematologicalTest haematologicalTest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private BloodPressureAndPulse bloodPressureAndPulse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private FinalDonorEvaluation finalDonorEvaluation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private BloodCollectionData bloodCollectionData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AdverseEvent adverseEvent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormProgress formProgress;

    private boolean submittedToLab = false;

    private LocalDateTime formStartedAt;
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    public void prePersist() {
        if (formProgress == null) {
            formProgress = FormProgress.NOT_STARTED;
        }
        if (formStartedAt == null) {
            formStartedAt = LocalDateTime.now();
        }

        lastUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

    public boolean isFormIncomplete() {
        return formProgress != FormProgress.COMPLETED;
    }

    public boolean isEligibleForNewDonation() {
        return formStartedAt == null || formStartedAt.plusMonths(3).isBefore(LocalDateTime.now());
    }
}
