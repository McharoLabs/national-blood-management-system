package com.nbts.management.donor_service.entity;

import com.nbts.management.donor_service.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @JoinColumn(nullable=false)
    private Donor donor;

    // A. Preliminary (Yes/No) Questionnaire
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo feelingWellToday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo eatenInLast4to8Hours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadMalariaLast2Weeks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadIllnessOrMedicationLast6Months;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadSurgeryLast6Months;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo receivedAnyVaccine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadTyphoidFever;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo feverWithAbdominalPainNauseaVomiting;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo pregnantOrBreastfeeding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo heartDiseaseOrHypertension;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo tuberculosisOrAsthma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo bleedingDisorders;

    @Enumerated(EnumType.STRING)
    private YesNo diabetes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo cancer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo chronicDisease;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo newSexPartner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo multipleSexPartners;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo partnerWithMultiplePartners;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo oralSex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo analSex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo sharedSharpObjects;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hospitalizedLast12Months;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadInjuryFromSharpObjects;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo tattooOrBodyPiercing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo selfInjectedOrUnregulatedInjection;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo stdInfections;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo sexualAssaultSurvivor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo exchangedSexForMoneyOrGoods;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo homosexualActivity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo receivedBloodTransfusion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo contactWithBloodOrBodyFluids;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo diagnosedWithHivHbvHcvSyphilis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hadSexWithInfectedPartner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo jaundiceOrYellowEyesOrUrine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo historyOfJaundice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo hepatitisTestLast6Months;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo traveledFarAndStayedLong;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YesNo donorBelievesBloodIsSafe;

    /**
     * B. Donor Declaration
     * Donor's Signature
     * Date of declaration
     * **/

    // C. Physical Examination & Vitals
    private Double weightKg;
    private Double heightCm;
    private String scaleUsedToMeasure;

    // D. Haematological Tests
    private Double haemoglobinLevel;

    private Double haematocrit;

    private Integer plateletsCount;

    @Enumerated(EnumType.STRING)
    private SerumProteinStatus serumProteinStatus;

    // E. Blood Pressure & Pulse

    private Integer pulse;

    private Integer bloodPressure;

    private String bpMachine;

    // F. Final Donor Evaluation

    @Enumerated(EnumType.STRING)
    private DonorStatus donorStatus;

    @Embedded
    private ReasonForDeferral reasonForDeferral;

    private String comment;

    // G. Blood Collection Data
    private LocalTime timeNeedleInserted;
    private LocalTime timeNeedleRemoved;

    private boolean venipunctureSuccessful;

    private boolean bloodCollectionUnsuccessful;

    private String smallAmountCollected;

    private String quantityOfBloodCollected;
    private String scaleUsed;
    private String apheresisMachineUsed;
    private String bloodBagType;
    private String bloodBagLotNumber;

    private LocalDate bloodBagExpiryDate;

    // H. Blood/Blood Product Type
    @Enumerated(EnumType.STRING)
    private BloodProductType bloodProductType;

    // I. Adverse Events
    @Enumerated(EnumType.STRING)
    private YesNo bloodCollectionIncident;

    @Enumerated(EnumType.STRING)
    private FaintingLevel fainted;

    private String additionalComments;

    // J. Collection Officer
    private String collectionOfficerName;
    private String collectionOfficerSignature;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormProgress formProgress;

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
    }

    public boolean isEligibleForNewDonation() {
        if (formStartedAt == null) {
            return true;
        }
        return formStartedAt.plusMonths(3).isBefore(LocalDateTime.now());
    }
}
