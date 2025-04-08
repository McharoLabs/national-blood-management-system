package com.nbts.management.donor_service.mapper;

import com.nbts.management.donor_service.dto.QuestionnaireResponseDTO;
import com.nbts.management.donor_service.dto.ReasonForDeferralDTO;
import com.nbts.management.donor_service.entity.Questionnaire;
import com.nbts.management.donor_service.entity.ReasonForDeferral;
import com.nbts.management.donor_service.enums.MedicalReason;

import java.util.stream.Collectors;

public class QuestionnaireMapper {
    public static QuestionnaireResponseDTO toQuestionnaireDTO(Questionnaire questionnaire) {
        QuestionnaireResponseDTO responseDTO = new QuestionnaireResponseDTO();

        responseDTO.setId(questionnaire.getId());
        responseDTO.setDonorId(questionnaire.getDonor() != null ? questionnaire.getDonor().getId() : null);

        // A. Preliminary
        responseDTO.setFeelingWellToday(questionnaire.getFeelingWellToday());
        responseDTO.setEatenInLast4to8Hours(questionnaire.getEatenInLast4to8Hours());
        responseDTO.setHadMalariaLast2Weeks(questionnaire.getHadMalariaLast2Weeks());
        responseDTO.setHadIllnessOrMedicationLast6Months(questionnaire.getHadIllnessOrMedicationLast6Months());
        responseDTO.setHadSurgeryLast6Months(questionnaire.getHadSurgeryLast6Months());
        responseDTO.setReceivedAnyVaccine(questionnaire.getReceivedAnyVaccine());
        responseDTO.setHadTyphoidFever(questionnaire.getHadTyphoidFever());
        responseDTO.setFeverWithAbdominalPainNauseaVomiting(questionnaire.getFeverWithAbdominalPainNauseaVomiting());
        responseDTO.setPregnantOrBreastfeeding(questionnaire.getPregnantOrBreastfeeding());
        responseDTO.setHeartDiseaseOrHypertension(questionnaire.getHeartDiseaseOrHypertension());
        responseDTO.setTuberculosisOrAsthma(questionnaire.getTuberculosisOrAsthma());
        responseDTO.setBleedingDisorders(questionnaire.getBleedingDisorders());
        responseDTO.setDiabetes(questionnaire.getDiabetes());
        responseDTO.setCancer(questionnaire.getCancer());
        responseDTO.setChronicDisease(questionnaire.getChronicDisease());
        responseDTO.setNewSexPartner(questionnaire.getNewSexPartner());
        responseDTO.setMultipleSexPartners(questionnaire.getMultipleSexPartners());
        responseDTO.setPartnerWithMultiplePartners(questionnaire.getPartnerWithMultiplePartners());
        responseDTO.setOralSex(questionnaire.getOralSex());
        responseDTO.setAnalSex(questionnaire.getAnalSex());
        responseDTO.setSharedSharpObjects(questionnaire.getSharedSharpObjects());
        responseDTO.setHospitalizedLast12Months(questionnaire.getHospitalizedLast12Months());
        responseDTO.setHadInjuryFromSharpObjects(questionnaire.getHadInjuryFromSharpObjects());
        responseDTO.setTattooOrBodyPiercing(questionnaire.getTattooOrBodyPiercing());
        responseDTO.setSelfInjectedOrUnregulatedInjection(questionnaire.getSelfInjectedOrUnregulatedInjection());
        responseDTO.setStdInfections(questionnaire.getStdInfections());
        responseDTO.setSexualAssaultSurvivor(questionnaire.getSexualAssaultSurvivor());
        responseDTO.setExchangedSexForMoneyOrGoods(questionnaire.getExchangedSexForMoneyOrGoods());
        responseDTO.setHomosexualActivity(questionnaire.getHomosexualActivity());
        responseDTO.setReceivedBloodTransfusion(questionnaire.getReceivedBloodTransfusion());
        responseDTO.setContactWithBloodOrBodyFluids(questionnaire.getContactWithBloodOrBodyFluids());
        responseDTO.setDiagnosedWithHivHbvHcvSyphilis(questionnaire.getDiagnosedWithHivHbvHcvSyphilis());
        responseDTO.setHadSexWithInfectedPartner(questionnaire.getHadSexWithInfectedPartner());
        responseDTO.setJaundiceOrYellowEyesOrUrine(questionnaire.getJaundiceOrYellowEyesOrUrine());
        responseDTO.setHistoryOfJaundice(questionnaire.getHistoryOfJaundice());
        responseDTO.setHepatitisTestLast6Months(questionnaire.getHepatitisTestLast6Months());
        responseDTO.setTraveledFarAndStayedLong(questionnaire.getTraveledFarAndStayedLong());
        responseDTO.setDonorBelievesBloodIsSafe(questionnaire.getDonorBelievesBloodIsSafe());

        // C. Physical Examination
        responseDTO.setWeightKg(questionnaire.getWeightKg());
        responseDTO.setHeightCm(questionnaire.getHeightCm());
        responseDTO.setScaleUsedToMeasure(questionnaire.getScaleUsedToMeasure());

        // D. Haematological Tests
        responseDTO.setHaemoglobinLevel(questionnaire.getHaemoglobinLevel());
        responseDTO.setHaematocrit(questionnaire.getHaematocrit());
        responseDTO.setPlateletsCount(questionnaire.getPlateletsCount());
        responseDTO.setSerumProteinStatus(questionnaire.getSerumProteinStatus());

        // E. Blood Pressure
        responseDTO.setPulse(questionnaire.getPulse());
        responseDTO.setBloodPressure(questionnaire.getBloodPressure());
        responseDTO.setBpMachine(questionnaire.getBpMachine());

        // F. Donor Evaluation
        responseDTO.setDonorStatus(questionnaire.getDonorStatus());

        ReasonForDeferral deferral = questionnaire.getReasonForDeferral();
        if (deferral != null) {
            ReasonForDeferralDTO deferralDTO = new ReasonForDeferralDTO();

            // Map medical reasons
            if (deferral.getMedicalReasons() != null) {
                deferralDTO.setMedicalReasons(
                        deferral.getMedicalReasons().stream()
                                .map(MedicalReason::getMedicalReason)
                                .collect(Collectors.toList())
                );
            }

            // Map other medical reason comment
            deferralDTO.setMedicalOtherReasonComment(deferral.getMedicalOtherReasonComment());

            // Map social reasons
            if (deferral.getSocialReasons() != null) {
                deferralDTO.setSocialReasons(deferral.getSocialReasons());
            }

            // Map social reason comment
            deferralDTO.setSocialReasonComment(deferral.getSocialReasonComment());

            // Set the mapped deferral details in the response DTO
            responseDTO.setReasonForDeferral(deferralDTO);
        }

        responseDTO.setComment(questionnaire.getComment());

        // G. Blood Collection
        responseDTO.setTimeNeedleInserted(questionnaire.getTimeNeedleInserted());
        responseDTO.setTimeNeedleRemoved(questionnaire.getTimeNeedleRemoved());
        responseDTO.setVenipunctureSuccessful(questionnaire.isVenipunctureSuccessful());
        responseDTO.setBloodCollectionUnsuccessful(questionnaire.isBloodCollectionUnsuccessful());
        responseDTO.setSmallAmountCollected(questionnaire.getSmallAmountCollected());
        responseDTO.setQuantityOfBloodCollected(questionnaire.getQuantityOfBloodCollected());
        responseDTO.setScaleUsed(questionnaire.getScaleUsed());
        responseDTO.setApheresisMachineUsed(questionnaire.getApheresisMachineUsed());
        responseDTO.setBloodBagType(questionnaire.getBloodBagType());
        responseDTO.setBloodBagLotNumber(questionnaire.getBloodBagLotNumber());
        responseDTO.setBloodBagExpiryDate(questionnaire.getBloodBagExpiryDate());

        // H. Product Type
        responseDTO.setBloodProductType(questionnaire.getBloodProductType());

        // I. Adverse Events
        responseDTO.setBloodCollectionIncident(questionnaire.getBloodCollectionIncident());
        responseDTO.setFainted(questionnaire.getFainted());
        responseDTO.setAdditionalComments(questionnaire.getAdditionalComments());

        // J. Officer Info
        responseDTO.setCollectionOfficerName(questionnaire.getCollectionOfficerName());
        responseDTO.setCollectionOfficerSignature(questionnaire.getCollectionOfficerSignature());
        responseDTO.setRemarks(questionnaire.getRemarks());

        return responseDTO;
    }
}
