package com.management.nationalblood.shared.dto;

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
    private Set<UUID> admin;
    private Set<RegionResponseDTO> regions;
}