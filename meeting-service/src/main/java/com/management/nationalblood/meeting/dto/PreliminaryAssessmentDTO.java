package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.YesNo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreliminaryAssessmentDTO {
    @NotNull private UUID questionnaireId;
    @NotNull private YesNo feelingWellToday;
    @NotNull private YesNo eatenInLast4to8Hours;
    @NotNull private YesNo hadMalariaLast2Weeks;
    @NotNull private YesNo hadIllnessOrMedicationLast6Months;
    @NotNull private YesNo hadSurgeryLast6Months;
    @NotNull private YesNo receivedAnyVaccine;
    @NotNull private YesNo hadTyphoidFever;
    @NotNull private YesNo feverWithAbdominalPainNauseaVomiting;
    @NotNull private YesNo pregnantOrBreastfeeding;
    @NotNull private YesNo heartDiseaseOrHypertension;
    @NotNull private YesNo tuberculosisOrAsthma;
    @NotNull private YesNo bleedingDisorders;
    @NotNull private YesNo diabetes;
    @NotNull private YesNo cancer;
    @NotNull private YesNo chronicDisease;
    @NotNull private YesNo newSexPartner;
    @NotNull private YesNo multipleSexPartners;
    @NotNull private YesNo partnerWithMultiplePartners;
    @NotNull private YesNo oralSex;
    @NotNull private YesNo analSex;
    @NotNull private YesNo sharedSharpObjects;
    @NotNull private YesNo hospitalizedLast12Months;
    @NotNull private YesNo hadInjuryFromSharpObjects;
    @NotNull private YesNo tattooOrBodyPiercing;
    @NotNull private YesNo selfInjectedOrUnregulatedInjection;
    @NotNull private YesNo stdInfections;
    @NotNull private YesNo sexualAssaultSurvivor;
    @NotNull private YesNo exchangedSexForMoneyOrGoods;
    @NotNull private YesNo homosexualActivity;
    @NotNull private YesNo receivedBloodTransfusion;
    @NotNull private YesNo contactWithBloodOrBodyFluids;
    @NotNull private YesNo diagnosedWithHivHbvHcvSyphilis;
    @NotNull private YesNo hadSexWithInfectedPartner;
    @NotNull private YesNo jaundiceOrYellowEyesOrUrine;
    @NotNull private YesNo historyOfJaundice;
    @NotNull private YesNo hepatitisTestLast6Months;
    @NotNull private YesNo traveledFarAndStayedLong;
    @NotNull private YesNo donorBelievesBloodIsSafe;
}
