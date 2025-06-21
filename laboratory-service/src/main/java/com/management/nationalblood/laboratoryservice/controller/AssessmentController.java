package com.management.nationalblood.laboratoryservice.controller;

import com.management.nationalblood.laboratoryservice.dto.AssessmentResponseDTO;
import com.management.nationalblood.laboratoryservice.dto.CreateLabTestResultDTO;
import com.management.nationalblood.laboratoryservice.dto.LaboratoryResponseDTO;
import com.management.nationalblood.laboratoryservice.exception.UnauthorizedAccessException;
import com.management.nationalblood.laboratoryservice.service.AssessmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("")
public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    private UUID getUserIdFromRequest(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedAccessException("User ID not found in request attributes");
        }
        return UUID.fromString(userId);
    }

    @GetMapping("/center/{centerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_LAB_TECHNICIAN')")
    public ResponseEntity<LaboratoryResponseDTO<Page<AssessmentResponseDTO>>> getPendingAssessmentsForTest(
            @PathVariable UUID centerId,
            @RequestParam(required = false) String bloodBagLotNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<AssessmentResponseDTO> assessments =
                assessmentService.getAssessmentForTest(centerId, bloodBagLotNumber, pageable);

        LaboratoryResponseDTO<Page<AssessmentResponseDTO>> response = LaboratoryResponseDTO.ok(
                assessments,
                "Assessments retrieved successfully",
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("lab-tests/complete")
    @PreAuthorize("hasAnyAuthority('ROLE_LAB_TECHNICIAN')")
    public ResponseEntity<LaboratoryResponseDTO<Map<String, Object>>> completeBloodTest(
            @RequestBody CreateLabTestResultDTO testResultDTO,
            HttpServletRequest request
    ) {
        LaboratoryResponseDTO<Map<String, Object>> response = LaboratoryResponseDTO.ok(
                Map.of("assessmentId", assessmentService.completeBloodTest(testResultDTO)),
                "Lab test result saved successfully",
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("donor/history")
    public ResponseEntity<LaboratoryResponseDTO<Page<AssessmentResponseDTO>>> getDonorHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpServletRequest request
    ) {
        UUID donorId = getUserIdFromRequest(request);
        System.out.print(donorId);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<AssessmentResponseDTO> assessments = assessmentService.getDonorAssessmentHistory(donorId, pageable);

        LaboratoryResponseDTO<Page<AssessmentResponseDTO>> response = LaboratoryResponseDTO.ok(
                assessments,
                "Donor history retrieved successfully",
                donorId.toString()
        );

        return ResponseEntity.ok(response);
    }

}
