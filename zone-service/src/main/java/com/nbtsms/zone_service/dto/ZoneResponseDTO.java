package com.nbtsms.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZoneResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private Set<RegionResponseDTO> regions;
}