package com.nbtsms.zone_service.dto;

import com.nbtsms.zone_service.entity.Zone;
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
public class RegionResponseDTO {
    private UUID id;
    private String name;
    private int totalCenter;
}