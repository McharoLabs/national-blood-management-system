package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.service.impl.CenterServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("centers")
public class CenterController {
    private final CenterServiceImpl centerService;

    public CenterController(CenterServiceImpl centerService) {
        this.centerService = centerService;
    }

    @GetMapping("{centerId}/exists")
    public boolean centerExists(@PathVariable("centerId") UUID centerId) {
        return centerService.centerExists(centerId);
    }

    @GetMapping("{centerId}/center")
    public ResponseEntity<?> getZone(@PathVariable UUID centerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            CenterResponseDTO center = centerService.getCenter(centerId);
            return ResponseEntity.ok(center);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createCenter(@Valid @RequestBody CreateCenterDTO createCenterDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            centerService.create(createCenterDTO);
            response.put("detail", "Center added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
