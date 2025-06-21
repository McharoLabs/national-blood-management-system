package com.management.nationalblood.laboratoryservice.mapper;

import com.management.nationalblood.laboratoryservice.dto.CreateLabTestResultDTO;
import com.management.nationalblood.laboratoryservice.dto.LabTestResultResponseDTO;
import com.management.nationalblood.laboratoryservice.entity.Assessment;
import com.management.nationalblood.laboratoryservice.entity.LabTestResult;

public class LabTestResultMapper {
    public static LabTestResultResponseDTO toResponse(LabTestResult entity) {
        return LabTestResultResponseDTO.builder()
                .hivTestResult(entity.isHivTestResult())
                .hepatitisBTestResult(entity.isHepatitisBTestResult())
                .hepatitisCTestResult(entity.isHepatitisCTestResult())
                .syphilisTestResult(entity.isSyphilisTestResult())
                .malariaTestResult(entity.isMalariaTestResult())
                .aboGroup(entity.getAboGroup())
                .rhFactor(entity.getRhFactor())
                .labTechnicianName(entity.getLabTechnicianName())
                .notes(entity.getNotes())
                .testedAt(entity.getTestedAt())
                .recordedAt(entity.getRecordedAt())
                .submittedToDonor(entity.isSubmittedToDonor())
                .build();
    }

    public static LabTestResult toEntity(CreateLabTestResultDTO dto, Assessment assessment) {
        if (dto == null) return null;

        LabTestResult labTestResult = new LabTestResult();

        labTestResult.setAssessment(assessment);

        labTestResult.setHivTestResult(dto.getHivTestResult());
        labTestResult.setHepatitisBTestResult(dto.getHepatitisBTestResult());
        labTestResult.setHepatitisCTestResult(dto.getHepatitisCTestResult());
        labTestResult.setSyphilisTestResult(dto.getSyphilisTestResult());
        labTestResult.setMalariaTestResult(dto.getMalariaTestResult());

        labTestResult.setAboGroup(dto.getAboGroup());
        labTestResult.setRhFactor(dto.getRhFactor());

        labTestResult.setNotes(dto.getNotes());

        labTestResult.setTestedAt(dto.getTestedAt());

        return labTestResult;
    }
}
