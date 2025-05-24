package com.management.nationalblood.meeting.entity;

import com.nbtsms.national_blood_management_system.questionnaire.enums.YesNo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "preliminary_questionnaires")
public class PreliminaryQuestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "preliminaryQuestionnaire")
    private Questionnaire questionnaire;

    @Enumerated(EnumType.STRING)
    private YesNo feelingWellToday;
    @Enumerated(EnumType.STRING)
    private YesNo eatenInLast4to8Hours;
    @Enumerated(EnumType.STRING)
    private YesNo hadMalariaLast2Weeks;
    @Enumerated(EnumType.STRING)
    private YesNo hadIllnessOrMedicationLast6Months;
    @Enumerated(EnumType.STRING)
    private YesNo hadSurgeryLast6Months;
    @Enumerated(EnumType.STRING)
    private YesNo receivedAnyVaccine;
    @Enumerated(EnumType.STRING)
    private YesNo hadTyphoidFever;
    @Enumerated(EnumType.STRING)
    private YesNo feverWithAbdominalPainNauseaVomiting;
    @Enumerated(EnumType.STRING)
    private YesNo pregnantOrBreastfeeding;
    @Enumerated(EnumType.STRING)
    private YesNo heartDiseaseOrHypertension;
    @Enumerated(EnumType.STRING)
    private YesNo tuberculosisOrAsthma;
    @Enumerated(EnumType.STRING)
    private YesNo bleedingDisorders;
    @Enumerated(EnumType.STRING)
    private YesNo diabetes;
    @Enumerated(EnumType.STRING)
    private YesNo cancer;
    @Enumerated(EnumType.STRING)
    private YesNo chronicDisease;
    @Enumerated(EnumType.STRING)
    private YesNo newSexPartner;
    @Enumerated(EnumType.STRING)
    private YesNo multipleSexPartners;
    @Enumerated(EnumType.STRING)
    private YesNo partnerWithMultiplePartners;
    @Enumerated(EnumType.STRING)
    private YesNo oralSex;
    @Enumerated(EnumType.STRING)
    private YesNo analSex;
    @Enumerated(EnumType.STRING)
    private YesNo sharedSharpObjects;
    @Enumerated(EnumType.STRING)
    private YesNo hospitalizedLast12Months;
    @Enumerated(EnumType.STRING)
    private YesNo hadInjuryFromSharpObjects;
    @Enumerated(EnumType.STRING)
    private YesNo tattooOrBodyPiercing;
    @Enumerated(EnumType.STRING)
    private YesNo selfInjectedOrUnregulatedInjection;
    @Enumerated(EnumType.STRING)
    private YesNo stdInfections;
    @Enumerated(EnumType.STRING)
    private YesNo sexualAssaultSurvivor;
    @Enumerated(EnumType.STRING)
    private YesNo exchangedSexForMoneyOrGoods;
    @Enumerated(EnumType.STRING)
    private YesNo homosexualActivity;
    @Enumerated(EnumType.STRING)
    private YesNo receivedBloodTransfusion;
    @Enumerated(EnumType.STRING)
    private YesNo contactWithBloodOrBodyFluids;
    @Enumerated(EnumType.STRING)
    private YesNo diagnosedWithHivHbvHcvSyphilis;
    @Enumerated(EnumType.STRING)
    private YesNo hadSexWithInfectedPartner;
    @Enumerated(EnumType.STRING)
    private YesNo jaundiceOrYellowEyesOrUrine;
    @Enumerated(EnumType.STRING)
    private YesNo historyOfJaundice;
    @Enumerated(EnumType.STRING)
    private YesNo hepatitisTestLast6Months;
    @Enumerated(EnumType.STRING)
    private YesNo traveledFarAndStayedLong;
    @Enumerated(EnumType.STRING)
    private YesNo donorBelievesBloodIsSafe;
}
