package com.nbts.management.donor_service.dto;

import com.nbts.management.donor_service.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireResponseDTO {
    private UUID id;

    private UUID donorId;

    // A. Preliminary Questionnaire
    private YesNo feelingWellToday;
    private YesNo eatenInLast4to8Hours;
    private YesNo hadMalariaLast2Weeks;
    private YesNo hadIllnessOrMedicationLast6Months;
    private YesNo hadSurgeryLast6Months;
    private YesNo receivedAnyVaccine;
    private YesNo hadTyphoidFever;
    private YesNo feverWithAbdominalPainNauseaVomiting;
    private YesNo pregnantOrBreastfeeding;
    private YesNo heartDiseaseOrHypertension;
    private YesNo tuberculosisOrAsthma;
    private YesNo bleedingDisorders;
    private YesNo diabetes;
    private YesNo cancer;
    private YesNo chronicDisease;
    private YesNo newSexPartner;
    private YesNo multipleSexPartners;
    private YesNo partnerWithMultiplePartners;
    private YesNo oralSex;
    private YesNo analSex;
    private YesNo sharedSharpObjects;
    private YesNo hospitalizedLast12Months;
    private YesNo hadInjuryFromSharpObjects;
    private YesNo tattooOrBodyPiercing;
    private YesNo selfInjectedOrUnregulatedInjection;
    private YesNo stdInfections;
    private YesNo sexualAssaultSurvivor;
    private YesNo exchangedSexForMoneyOrGoods;
    private YesNo homosexualActivity;
    private YesNo receivedBloodTransfusion;
    private YesNo contactWithBloodOrBodyFluids;
    private YesNo diagnosedWithHivHbvHcvSyphilis;
    private YesNo hadSexWithInfectedPartner;
    private YesNo jaundiceOrYellowEyesOrUrine;
    private YesNo historyOfJaundice;
    private YesNo hepatitisTestLast6Months;
    private YesNo traveledFarAndStayedLong;
    private YesNo donorBelievesBloodIsSafe;

    // C. Physical Examination & Vitals
    private Double weightKg;
    private Double heightCm;
    private String scaleUsedToMeasure;

    // D. Haematological Tests
    private Double haemoglobinLevel;
    private Double haematocrit;
    private Integer plateletsCount;
    private SerumProteinStatus serumProteinStatus;

    // E. Blood Pressure & Pulse
    private Integer pulse;
    private Integer bloodPressure;
    private String bpMachine;

    // F. Final Donor Evaluation
    private DonorStatus donorStatus;
    private ReasonForDeferralDTO reasonForDeferral;
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
    private BloodProductType bloodProductType;

    // I. Adverse Events
    private YesNo bloodCollectionIncident;
    private FaintingLevel fainted;
    private String additionalComments;

    // J. Collection Officer
    private String collectionOfficerName;
    private String collectionOfficerSignature;
    private String remarks;
}
