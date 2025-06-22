package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.dto.ZoneResponseWrapperDTO;
import com.nbtsms.zone_service.service.impl.RegionServiceImpl;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("regions")
public class RegionController {
    private final RegionServiceImpl regionService;

    public RegionController(RegionServiceImpl regionService) {
        this.regionService = regionService;
    }

    @GetMapping("{regionId}/exists")
    public boolean regionExists(@PathVariable("regionId") UUID regionId) {
        return regionService.regionExists(regionId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    public ResponseEntity<ZoneResponseWrapperDTO<Map<String, Object>>> createRegion(
            @Valid @RequestBody CreateRegionDTO createRegionDTO,
            HttpServletRequest request
    ) {
        ZoneResponseWrapperDTO<Map<String, Object>> response = ZoneResponseWrapperDTO.ok(
                Map.of("regionId", regionService.create(createRegionDTO)),
                "Region added successfully to the zone",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{zoneId}/zone")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<ZoneResponseWrapperDTO<Page<RegionResponseDTO>>> getAllByZone(
            @PathVariable UUID zoneId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        ZoneResponseWrapperDTO<Page<RegionResponseDTO>> response = ZoneResponseWrapperDTO.ok(
                regionService.getAllByZone(name, zoneId, pageable),
                "Regions retrieved successful",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("region/{zoneId}/all")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<ZoneResponseWrapperDTO<List<RegionResponseDTO>>> getRegionsByZoneId(
            @PathVariable UUID zoneId,
            HttpServletRequest request
    ) {
        ZoneResponseWrapperDTO<List<RegionResponseDTO>> response = ZoneResponseWrapperDTO.ok(
                regionService.getRegionsByZone(zoneId),
                "Regions retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{regionId}/region")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<ZoneResponseWrapperDTO<RegionResponseDTO>> getRegionById(
            @PathVariable UUID regionId,
            HttpServletRequest request
    ) {
        ZoneResponseWrapperDTO<RegionResponseDTO> response = ZoneResponseWrapperDTO.ok(
                regionService.getRegionById(regionId),
                "Region retrieved successful",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
