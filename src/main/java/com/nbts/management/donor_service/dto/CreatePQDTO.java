package com.nbts.management.donor_service.dto;

import com.nbts.management.donor_service.enums.YesNo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePQDTO {

    @NotNull(message = "Donor ID is required")
    private UUID donorId;

    @NotNull(message = "Please specify if the donor is feeling well today")
    private YesNo feelingWellToday;

    @NotNull(message = "Please indicate if the donor has eaten in the last 4 to 8 hours")
    private YesNo eatenInLast4to8Hours;

    @NotNull(message = "Please indicate if the donor had malaria in the last 2 weeks")
    private YesNo hadMalariaLast2Weeks;

    @NotNull(message = "Please indicate if the donor had any illness or medication in the last 6 months")
    private YesNo hadIllnessOrMedicationLast6Months;

    @NotNull(message = "Please indicate if the donor had surgery in the last 6 months")
    private YesNo hadSurgeryLast6Months;

    @NotNull(message = "Please indicate if the donor received any vaccine")
    private YesNo receivedAnyVaccine;

    @NotNull(message = "Please indicate if the donor had typhoid fever")
    private YesNo hadTyphoidFever;

    @NotNull(message = "Please indicate if the donor had fever with abdominal pain, nausea, or vomiting")
    private YesNo feverWithAbdominalPainNauseaVomiting;

    @NotNull(message = "Please indicate if the donor is pregnant or breastfeeding")
    private YesNo pregnantOrBreastfeeding;

    @NotNull(message = "Please indicate if the donor has heart disease or hypertension")
    private YesNo heartDiseaseOrHypertension;

    @NotNull(message = "Please indicate if the donor has tuberculosis or asthma")
    private YesNo tuberculosisOrAsthma;

    @NotNull(message = "Please indicate if the donor has any bleeding disorders")
    private YesNo bleedingDisorders;

    private YesNo diabetes;

    @NotNull(message = "Please indicate if the donor has cancer")
    private YesNo cancer;

    @NotNull(message = "Please indicate if the donor has any chronic disease")
    private YesNo chronicDisease;

    @NotNull(message = "Please indicate if the donor had a new sex partner recently")
    private YesNo newSexPartner;

    @NotNull(message = "Please indicate if the donor had multiple sex partners")
    private YesNo multipleSexPartners;

    @NotNull(message = "Please indicate if the donor’s partner had multiple partners")
    private YesNo partnerWithMultiplePartners;

    @NotNull(message = "Please indicate if the donor had oral sex")
    private YesNo oralSex;

    @NotNull(message = "Please indicate if the donor had anal sex")
    private YesNo analSex;

    @NotNull(message = "Please indicate if the donor shared sharp objects")
    private YesNo sharedSharpObjects;

    @NotNull(message = "Please indicate if the donor was hospitalized in the last 12 months")
    private YesNo hospitalizedLast12Months;

    @NotNull(message = "Please indicate if the donor had injury from sharp objects")
    private YesNo hadInjuryFromSharpObjects;

    @NotNull(message = "Please indicate if the donor had a tattoo or body piercing")
    private YesNo tattooOrBodyPiercing;

    @NotNull(message = "Please indicate if the donor self-injected or received unregulated injection")
    private YesNo selfInjectedOrUnregulatedInjection;

    @NotNull(message = "Please indicate if the donor had any sexually transmitted diseases")
    private YesNo stdInfections;

    @NotNull(message = "Please indicate if the donor is a sexual assault survivor")
    private YesNo sexualAssaultSurvivor;

    @NotNull(message = "Please indicate if the donor exchanged sex for money or goods")
    private YesNo exchangedSexForMoneyOrGoods;

    @NotNull(message = "Please indicate if the donor participated in homosexual activity")
    private YesNo homosexualActivity;

    @NotNull(message = "Please indicate if the donor received blood transfusion")
    private YesNo receivedBloodTransfusion;

    @NotNull(message = "Please indicate if the donor had contact with blood or body fluids")
    private YesNo contactWithBloodOrBodyFluids;

    @NotNull(message = "Please indicate if the donor was diagnosed with HIV, HBV, HCV, or syphilis")
    private YesNo diagnosedWithHivHbvHcvSyphilis;

    @NotNull(message = "Please indicate if the donor had sex with an infected partner")
    private YesNo hadSexWithInfectedPartner;

    @NotNull(message = "Please indicate if the donor had jaundice, yellow eyes, or yellow urine")
    private YesNo jaundiceOrYellowEyesOrUrine;

    @NotNull(message = "Please indicate if the donor has a history of jaundice")
    private YesNo historyOfJaundice;

    @NotNull(message = "Please indicate if the donor had a hepatitis test in the last 6 months")
    private YesNo hepatitisTestLast6Months;

    @NotNull(message = "Please indicate if the donor traveled far and stayed long")
    private YesNo traveledFarAndStayedLong;

    @NotNull(message = "Please indicate if the donor believes their blood is safe for donation")
    private YesNo donorBelievesBloodIsSafe;
}
