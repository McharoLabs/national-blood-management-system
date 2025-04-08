package com.nbts.management.donor_service.mapper;

import com.nbts.management.donor_service.dto.CreatePQDTO;
import com.nbts.management.donor_service.dto.QuestionnaireResponseDTO;
import com.nbts.management.donor_service.entity.Questionnaire;

public class PQMapper {
    public static Questionnaire toEntity(CreatePQDTO createPQDTO) {
        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setFeelingWellToday(createPQDTO.getFeelingWellToday());
        questionnaire.setEatenInLast4to8Hours(createPQDTO.getEatenInLast4to8Hours());
        questionnaire.setHadMalariaLast2Weeks(createPQDTO.getHadMalariaLast2Weeks());
        questionnaire.setHadIllnessOrMedicationLast6Months(createPQDTO.getHadIllnessOrMedicationLast6Months());
        questionnaire.setHadSurgeryLast6Months(createPQDTO.getHadSurgeryLast6Months());
        questionnaire.setReceivedAnyVaccine(createPQDTO.getReceivedAnyVaccine());
        questionnaire.setHadTyphoidFever(createPQDTO.getHadTyphoidFever());
        questionnaire.setFeverWithAbdominalPainNauseaVomiting(createPQDTO.getFeverWithAbdominalPainNauseaVomiting());
        questionnaire.setPregnantOrBreastfeeding(createPQDTO.getPregnantOrBreastfeeding());
        questionnaire.setHeartDiseaseOrHypertension(createPQDTO.getHeartDiseaseOrHypertension());
        questionnaire.setTuberculosisOrAsthma(createPQDTO.getTuberculosisOrAsthma());
        questionnaire.setBleedingDisorders(createPQDTO.getBleedingDisorders());
        questionnaire.setDiabetes(createPQDTO.getDiabetes());
        questionnaire.setCancer(createPQDTO.getCancer());
        questionnaire.setChronicDisease(createPQDTO.getChronicDisease());
        questionnaire.setNewSexPartner(createPQDTO.getNewSexPartner());
        questionnaire.setMultipleSexPartners(createPQDTO.getMultipleSexPartners());
        questionnaire.setPartnerWithMultiplePartners(createPQDTO.getPartnerWithMultiplePartners());
        questionnaire.setOralSex(createPQDTO.getOralSex());
        questionnaire.setAnalSex(createPQDTO.getAnalSex());
        questionnaire.setSharedSharpObjects(createPQDTO.getSharedSharpObjects());
        questionnaire.setHospitalizedLast12Months(createPQDTO.getHospitalizedLast12Months());
        questionnaire.setHadInjuryFromSharpObjects(createPQDTO.getHadInjuryFromSharpObjects());
        questionnaire.setTattooOrBodyPiercing(createPQDTO.getTattooOrBodyPiercing());
        questionnaire.setSelfInjectedOrUnregulatedInjection(createPQDTO.getSelfInjectedOrUnregulatedInjection());
        questionnaire.setStdInfections(createPQDTO.getStdInfections());
        questionnaire.setSexualAssaultSurvivor(createPQDTO.getSexualAssaultSurvivor());
        questionnaire.setExchangedSexForMoneyOrGoods(createPQDTO.getExchangedSexForMoneyOrGoods());
        questionnaire.setHomosexualActivity(createPQDTO.getHomosexualActivity());
        questionnaire.setReceivedBloodTransfusion(createPQDTO.getReceivedBloodTransfusion());
        questionnaire.setContactWithBloodOrBodyFluids(createPQDTO.getContactWithBloodOrBodyFluids());
        questionnaire.setDiagnosedWithHivHbvHcvSyphilis(createPQDTO.getDiagnosedWithHivHbvHcvSyphilis());
        questionnaire.setHadSexWithInfectedPartner(createPQDTO.getHadSexWithInfectedPartner());
        questionnaire.setJaundiceOrYellowEyesOrUrine(createPQDTO.getJaundiceOrYellowEyesOrUrine());
        questionnaire.setHistoryOfJaundice(createPQDTO.getHistoryOfJaundice());
        questionnaire.setHepatitisTestLast6Months(createPQDTO.getHepatitisTestLast6Months());
        questionnaire.setTraveledFarAndStayedLong(createPQDTO.getTraveledFarAndStayedLong());
        questionnaire.setDonorBelievesBloodIsSafe(createPQDTO.getDonorBelievesBloodIsSafe());

        return questionnaire;
    }

}
