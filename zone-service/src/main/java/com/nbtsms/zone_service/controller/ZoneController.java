package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.AssignZoneAdminDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.service.impl.ZoneServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("zones")
public class ZoneController {
    private final ZoneServiceImpl zoneService;
    private final WebClient.Builder webClientBuilder;

    public ZoneController(ZoneServiceImpl zoneService, WebClient.Builder webClientBuilder) {
        this.zoneService = zoneService;
        this.webClientBuilder = webClientBuilder;
    }

    @DeleteMapping("/zone/{zone-id}/remove-admin")
    public ResponseEntity<Map<String, String>> removeZoneAdmin(@PathVariable("zone-id") UUID zoneId) {
        Map<String, String> response = new HashMap<>();

        try {
            zoneService.removeZoneAdmin(zoneId);
            response.put("detail", "Zone admin removed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/zone/{zone-id}/assign-admin")
    public ResponseEntity<Map<String, String>> assignZoneAdmin(
            @PathVariable("zone-id") UUID zoneId,
            @Valid @RequestBody AssignZoneAdminDTO adminDTO) {

        Map<String, String> response = new HashMap<>();

        Boolean result = webClientBuilder.build().get()
                .uri("http://identity-service/users/user/exists/{id}", adminDTO.getAdminId())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.FALSE.equals(result)) {
            response.put("adminId", "This user does not exist");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            zoneService.assignZoneAdmin(adminDTO, zoneId);
            response.put("detail", "Admin assigned successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("detail", "Internal server error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createZone(@Valid @RequestBody CreateZoneDTO createZoneDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            zoneService.create(createZoneDTO);
            response.put("detail", "Zone added successfully");
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
