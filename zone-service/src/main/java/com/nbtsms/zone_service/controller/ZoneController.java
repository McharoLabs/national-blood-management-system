package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.AssignZoneAdminDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.service.impl.ZoneServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class ZoneController {

    private final ZoneServiceImpl zoneService;

    @Autowired
    public ZoneController(ZoneServiceImpl zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createZone(@Valid @RequestBody CreateZoneDTO createZoneDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            UUID zoneId = zoneService.addZone(createZoneDTO);
            response.put("zoneId", zoneId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{zoneId}/exists")
    public boolean zoneExists(@PathVariable("zoneId") UUID zoneId) {
        return zoneService.zoneExists(zoneId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("user")
    public String test() {
        return "Hello user";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("all")
    public ResponseEntity<?> getAllZones() {
        try {
            List<ZoneResponseDTO> zones = zoneService.getZones();
            return ResponseEntity.ok(zones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("detail", e.getMessage()));
        }
    }

    @GetMapping("{zoneId}/zone")
    public ResponseEntity<?> getZone(@PathVariable UUID zoneId) {
        Map<String, Object> response = new HashMap<>();
        try {
            ZoneResponseDTO zone = zoneService.getZone(zoneId);
            return ResponseEntity.ok(zone);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // fallback
    public ResponseEntity<Map<String, String>> fallbackAssignAdmin(UUID zoneId, AssignZoneAdminDTO adminDTO, Throwable throwable) {
        Map<String, String> fallbackResponse = new HashMap<>();
        fallbackResponse.put("detail", "Oops! Service is unavailable. Please try again later.");
        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
