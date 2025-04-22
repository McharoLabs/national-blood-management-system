package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.dto.ZoneIdDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.exception.BadRequestException;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.impl.CenterServiceImpl;
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

import java.util.HashMap;
import java.util.List;
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
    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
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

    @PostMapping("add")
    public ResponseEntity<Map<String, Object>> createCenter(@Valid @RequestBody CreateCenterDTO createCenterDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            UUID centerId = centerService.create(createCenterDTO);
            response.put("centerId", centerId);
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

    @GetMapping("all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCenters(
            HttpServletRequest request,
            @Valid @RequestBody ZoneIdDTO zoneIdDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
            ) {
        //UUID staffId = UUID.fromString((String) request.getAttribute("userId"));

        Map<String, Object> response = new HashMap<>();
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<CenterResponseDTO> centers = centerService.getAllCenterByZoneId(zoneIdDTO.getZoneId(), pageable);
            return new ResponseEntity<>(centers, HttpStatus.OK);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    @GetMapping("{zoneId}/center/{centerId}/is-associated")
    public boolean centerBelongsToZone(
            @PathVariable("zoneId") UUID zoneId,
            @PathVariable("centerId") UUID centerId
    ) {
        return centerService.centerBelongToZone(zoneId, centerId);
    }

}
