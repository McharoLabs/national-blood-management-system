package com.nbtsms.zone_service.dto;

import com.nbtsms.zone_service.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenterResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private RegionResponseDTO region;
}