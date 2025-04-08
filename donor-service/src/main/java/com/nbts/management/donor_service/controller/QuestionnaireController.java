package com.nbts.management.donor_service.controller;

import com.nbts.management.donor_service.dto.CreatePQDTO;
import com.nbts.management.donor_service.dto.QuestionnaireResponseDTO;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;
import com.nbts.management.donor_service.service.impl.PQServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("questionnaires")
public class QuestionnaireController {

    private final PQServiceImpl pqService;

    public QuestionnaireController(PQServiceImpl pqService) {
        this.pqService = pqService;
    }

    @PostMapping("/create-pq")
    public ResponseEntity<Map<String, Object>> createPQ(@Valid @RequestBody CreatePQDTO createPQDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            QuestionnaireResponseDTO responseDTO = pqService.createPQ(createPQDTO);

            response.put("detail", "Preliminary data inserted successfully");

            response.put("data", responseDTO);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("detail", "An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
