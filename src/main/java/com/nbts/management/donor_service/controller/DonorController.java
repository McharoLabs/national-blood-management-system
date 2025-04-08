package com.nbts.management.donor_service.controller;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.service.impl.DonorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("donors")
public class DonorController {
    private final DonorServiceImpl donorService;

    public DonorController(DonorServiceImpl donorService) {
        this.donorService = donorService;
    }


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateDonorDTO createDonorDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            DonorResponseDTO responseDTO = donorService.createDonor(createDonorDTO);
            response.put("detail", "Donor added successfully");
            response.put("data", responseDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
