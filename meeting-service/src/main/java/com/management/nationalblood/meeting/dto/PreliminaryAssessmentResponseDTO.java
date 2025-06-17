package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.YesNo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreliminaryAssessmentResponseDTO {
    private UUID id;

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
}
