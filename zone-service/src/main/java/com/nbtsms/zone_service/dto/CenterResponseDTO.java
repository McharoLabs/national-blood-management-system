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
public class CenterResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private RegionResponseDTO region;
    private Set<CenterStaffResponseDTO> staffs;
}