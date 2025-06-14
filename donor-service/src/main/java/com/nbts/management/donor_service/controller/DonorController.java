package com.nbts.management.donor_service.controller;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.dto.DonorServiceResponseDTO;
import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.service.impl.DonorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class DonorController {

    private final DonorServiceImpl donorService;

    public DonorController(DonorServiceImpl donorService) {
        this.donorService = donorService;
    }

    @PostMapping()
    public ResponseEntity<DonorServiceResponseDTO<Map<String, Object>>> create(
            @Valid @RequestBody CreateDonorDTO createDonorDTO,
            HttpServletRequest request
    ) {
        DonorServiceResponseDTO<Map<String, Object>> response = DonorServiceResponseDTO.ok(
                Map.of("donorId", donorService.createDonor(createDonorDTO)),
                "Donor created successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DonorServiceResponseDTO<Page<DonorResponseDTO>>> getAllDonors(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Gender gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        DonorServiceResponseDTO<Page<DonorResponseDTO>> response = DonorServiceResponseDTO.ok(
                donorService.getAllDonors(pageable, fullName, gender),
                "Donor fetched successfully",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{donorId}/donor")
    public ResponseEntity<DonorServiceResponseDTO<DonorResponseDTO>> getDonorById(
            @PathVariable UUID donorId,
            HttpServletRequest request
    ) {
        DonorServiceResponseDTO<DonorResponseDTO> response = DonorServiceResponseDTO.ok(
                donorService.getDonor(donorId),
                "Donor retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    @GetMapping("/{donorId}/exists")
    public boolean checkDonorExists(@PathVariable UUID donorId) {
        return donorService.donorExists(donorId);
    }
}
