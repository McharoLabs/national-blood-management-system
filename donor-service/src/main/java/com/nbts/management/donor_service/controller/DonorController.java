package com.nbts.management.donor_service.controller;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;
import com.nbts.management.donor_service.service.impl.DonorServiceImpl;
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
@RequestMapping("/donors")
public class DonorController {

    private final DonorServiceImpl donorService;

    public DonorController(DonorServiceImpl donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateDonorDTO createDonorDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            UUID donorId = donorService.createDonor(createDonorDTO);
            response.put("donorId", donorId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDonors(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Gender gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<DonorResponseDTO> donorsPage = donorService.getAllDonors(pageable, fullName, gender);

        Map<String, Object> response = new HashMap<>();
        response.put("data", donorsPage.getContent());
        response.put("currentPage", donorsPage.getNumber());
        response.put("totalItems", donorsPage.getTotalElements());
        response.put("totalPages", donorsPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{donorId}")
    public ResponseEntity<?> getDonorById(@PathVariable UUID donorId) {
        try {
            DonorResponseDTO donor = donorService.getDonor(donorId);
            return ResponseEntity.ok(donor);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getErrorMessages());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    @GetMapping("/{donorId}/exists")
    public boolean checkDonorExists(@PathVariable UUID donorId) {
        return donorService.donorExists(donorId);
    }
}
