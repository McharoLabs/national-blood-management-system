package com.management.nationalblood.meeting.mapper;


import com.management.nationalblood.meeting.dto.PreliminaryAssessmentDTO;
import com.management.nationalblood.meeting.dto.PreliminaryAssessmentResponseDTO;
import com.management.nationalblood.meeting.entity.PreliminaryQuestionnaire;

public class PreliminaryAssessmentMapper {
    public static PreliminaryQuestionnaire toEntity(PreliminaryAssessmentDTO preliminaryQuestionnaireDTO) {
        PreliminaryQuestionnaire questionnaire = new PreliminaryQuestionnaire();

        questionnaire.setFeelingWellToday(preliminaryQuestionnaireDTO.getFeelingWellToday());
        questionnaire.setEatenInLast4to8Hours(preliminaryQuestionnaireDTO.getEatenInLast4to8Hours());
        questionnaire.setHadMalariaLast2Weeks(preliminaryQuestionnaireDTO.getHadMalariaLast2Weeks());
        questionnaire.setHadIllnessOrMedicationLast6Months(preliminaryQuestionnaireDTO.getHadIllnessOrMedicationLast6Months());
        questionnaire.setHadSurgeryLast6Months(preliminaryQuestionnaireDTO.getHadSurgeryLast6Months());
        questionnaire.setReceivedAnyVaccine(preliminaryQuestionnaireDTO.getReceivedAnyVaccine());
        questionnaire.setHadTyphoidFever(preliminaryQuestionnaireDTO.getHadTyphoidFever());
        questionnaire.setFeverWithAbdominalPainNauseaVomiting(preliminaryQuestionnaireDTO.getFeverWithAbdominalPainNauseaVomiting());
        questionnaire.setPregnantOrBreastfeeding(preliminaryQuestionnaireDTO.getPregnantOrBreastfeeding());
        questionnaire.setHeartDiseaseOrHypertension(preliminaryQuestionnaireDTO.getHeartDiseaseOrHypertension());
        questionnaire.setTuberculosisOrAsthma(preliminaryQuestionnaireDTO.getTuberculosisOrAsthma());
        questionnaire.setBleedingDisorders(preliminaryQuestionnaireDTO.getBleedingDisorders());
        questionnaire.setDiabetes(preliminaryQuestionnaireDTO.getDiabetes());
        questionnaire.setCancer(preliminaryQuestionnaireDTO.getCancer());
        questionnaire.setChronicDisease(preliminaryQuestionnaireDTO.getChronicDisease());
        questionnaire.setNewSexPartner(preliminaryQuestionnaireDTO.getNewSexPartner());
        questionnaire.setMultipleSexPartners(preliminaryQuestionnaireDTO.getMultipleSexPartners());
        questionnaire.setPartnerWithMultiplePartners(preliminaryQuestionnaireDTO.getPartnerWithMultiplePartners());
        questionnaire.setOralSex(preliminaryQuestionnaireDTO.getOralSex());
        questionnaire.setAnalSex(preliminaryQuestionnaireDTO.getAnalSex());
        questionnaire.setSharedSharpObjects(preliminaryQuestionnaireDTO.getSharedSharpObjects());
        questionnaire.setHospitalizedLast12Months(preliminaryQuestionnaireDTO.getHospitalizedLast12Months());
        questionnaire.setHadInjuryFromSharpObjects(preliminaryQuestionnaireDTO.getHadInjuryFromSharpObjects());
        questionnaire.setTattooOrBodyPiercing(preliminaryQuestionnaireDTO.getTattooOrBodyPiercing());
        questionnaire.setSelfInjectedOrUnregulatedInjection(preliminaryQuestionnaireDTO.getSelfInjectedOrUnregulatedInjection());
        questionnaire.setStdInfections(preliminaryQuestionnaireDTO.getStdInfections());
        questionnaire.setSexualAssaultSurvivor(preliminaryQuestionnaireDTO.getSexualAssaultSurvivor());
        questionnaire.setExchangedSexForMoneyOrGoods(preliminaryQuestionnaireDTO.getExchangedSexForMoneyOrGoods());
        questionnaire.setHomosexualActivity(preliminaryQuestionnaireDTO.getHomosexualActivity());
        questionnaire.setReceivedBloodTransfusion(preliminaryQuestionnaireDTO.getReceivedBloodTransfusion());
        questionnaire.setContactWithBloodOrBodyFluids(preliminaryQuestionnaireDTO.getContactWithBloodOrBodyFluids());
        questionnaire.setDiagnosedWithHivHbvHcvSyphilis(preliminaryQuestionnaireDTO.getDiagnosedWithHivHbvHcvSyphilis());
        questionnaire.setHadSexWithInfectedPartner(preliminaryQuestionnaireDTO.getHadSexWithInfectedPartner());
        questionnaire.setJaundiceOrYellowEyesOrUrine(preliminaryQuestionnaireDTO.getJaundiceOrYellowEyesOrUrine());
        questionnaire.setHistoryOfJaundice(preliminaryQuestionnaireDTO.getHistoryOfJaundice());
        questionnaire.setHepatitisTestLast6Months(preliminaryQuestionnaireDTO.getHepatitisTestLast6Months());
        questionnaire.setTraveledFarAndStayedLong(preliminaryQuestionnaireDTO.getTraveledFarAndStayedLong());
        questionnaire.setDonorBelievesBloodIsSafe(preliminaryQuestionnaireDTO.getDonorBelievesBloodIsSafe());

        return questionnaire;
    }


    public static PreliminaryAssessmentResponseDTO toResponse(PreliminaryQuestionnaire q) {
        return PreliminaryAssessmentResponseDTO.builder()
                .id(q.getId())
                .feelingWellToday(q.getFeelingWellToday())
                .eatenInLast4to8Hours(q.getEatenInLast4to8Hours())
                .hadMalariaLast2Weeks(q.getHadMalariaLast2Weeks())
                .hadIllnessOrMedicationLast6Months(q.getHadIllnessOrMedicationLast6Months())
                .hadSurgeryLast6Months(q.getHadSurgeryLast6Months())
                .receivedAnyVaccine(q.getReceivedAnyVaccine())
                .hadTyphoidFever(q.getHadTyphoidFever())
                .feverWithAbdominalPainNauseaVomiting(q.getFeverWithAbdominalPainNauseaVomiting())
                .pregnantOrBreastfeeding(q.getPregnantOrBreastfeeding())
                .heartDiseaseOrHypertension(q.getHeartDiseaseOrHypertension())
                .tuberculosisOrAsthma(q.getTuberculosisOrAsthma())
                .bleedingDisorders(q.getBleedingDisorders())
                .diabetes(q.getDiabetes())
                .cancer(q.getCancer())
                .chronicDisease(q.getChronicDisease())
                .newSexPartner(q.getNewSexPartner())
                .multipleSexPartners(q.getMultipleSexPartners())
                .partnerWithMultiplePartners(q.getPartnerWithMultiplePartners())
                .oralSex(q.getOralSex())
                .analSex(q.getAnalSex())
                .sharedSharpObjects(q.getSharedSharpObjects())
                .hospitalizedLast12Months(q.getHospitalizedLast12Months())
                .hadInjuryFromSharpObjects(q.getHadInjuryFromSharpObjects())
                .tattooOrBodyPiercing(q.getTattooOrBodyPiercing())
                .selfInjectedOrUnregulatedInjection(q.getSelfInjectedOrUnregulatedInjection())
                .stdInfections(q.getStdInfections())
                .sexualAssaultSurvivor(q.getSexualAssaultSurvivor())
                .exchangedSexForMoneyOrGoods(q.getExchangedSexForMoneyOrGoods())
                .homosexualActivity(q.getHomosexualActivity())
                .receivedBloodTransfusion(q.getReceivedBloodTransfusion())
                .contactWithBloodOrBodyFluids(q.getContactWithBloodOrBodyFluids())
                .diagnosedWithHivHbvHcvSyphilis(q.getDiagnosedWithHivHbvHcvSyphilis())
                .hadSexWithInfectedPartner(q.getHadSexWithInfectedPartner())
                .jaundiceOrYellowEyesOrUrine(q.getJaundiceOrYellowEyesOrUrine())
                .historyOfJaundice(q.getHistoryOfJaundice())
                .hepatitisTestLast6Months(q.getHepatitisTestLast6Months())
                .traveledFarAndStayedLong(q.getTraveledFarAndStayedLong())
                .donorBelievesBloodIsSafe(q.getDonorBelievesBloodIsSafe())
                .build();
    }
}
