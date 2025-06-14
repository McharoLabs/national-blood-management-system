package com.nbtsms.zone_service.controller;

import com.nbtsms.zone_service.dto.AdminResponseDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.dto.ZoneResponseWrapperDTO;
import com.nbtsms.zone_service.service.impl.ZoneServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("{zoneId}/admins")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    public ResponseEntity<ZoneResponseWrapperDTO<Page<AdminResponseDTO>>> getAdminsByZone(
            @PathVariable UUID zoneId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            HttpServletRequest request
    )  {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        ZoneResponseWrapperDTO<Page<AdminResponseDTO>> response = ZoneResponseWrapperDTO.ok(
                zoneService.zoneAdmins(zoneId, name, pageable),
                "Admins retrieved successfully",
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_USER')")
    @PostMapping
    public ResponseEntity<ZoneResponseWrapperDTO<Map<String, Object>>> createZone(
            @Valid @RequestBody CreateZoneDTO createZoneDTO,
            HttpServletRequest request
    ) {
        ZoneResponseWrapperDTO<Map<String, Object>> response = ZoneResponseWrapperDTO.ok(
                Map.of("zoneId", zoneService.addZone(createZoneDTO)),
                "Zone created successful", request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    @GetMapping
    public ResponseEntity<ZoneResponseWrapperDTO<List<ZoneResponseDTO>>> getAllZones(HttpServletRequest request) {
        ZoneResponseWrapperDTO<List<ZoneResponseDTO>> response = ZoneResponseWrapperDTO.ok(
                zoneService.getZones(),
                "Zones retrieved successful",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    @GetMapping("{zoneId}/zone")
    public ResponseEntity<ZoneResponseWrapperDTO<ZoneResponseDTO>> getZone(
            @PathVariable UUID zoneId,
            HttpServletRequest request
    ) {
        ZoneResponseWrapperDTO<ZoneResponseDTO> response = ZoneResponseWrapperDTO.ok(
                zoneService.getZone(zoneId),
                "Zone retrieved successful",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    @GetMapping("{zoneId}/exists")
    public boolean zoneExists(@PathVariable("zoneId") UUID zoneId) {
        return zoneService.zoneExists(zoneId);
    }

    @GetMapping("{zoneId}/zone-id")
    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    public UUID getZoneIdById(@PathVariable UUID zoneId) {
        return zoneService.getZoneIdById(zoneId);
    }
}
