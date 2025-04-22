package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.entity.Zone;

import java.util.Set;
import java.util.stream.Collectors;

public class ZoneMapper {
    public static Zone toEntity(CreateZoneDTO createZoneDTO) {
        Zone zone = new Zone();

        zone.setName(createZoneDTO.getName());

        return zone;
    }

    public static ZoneResponseDTO toResponse(Zone zone) {
        ZoneResponseDTO zoneResponseDTO = new ZoneResponseDTO();

        zoneResponseDTO.setId(zone.getId());
        zoneResponseDTO.setName(zone.getName());

        Set<RegionResponseDTO> regionResponseDTOs =
                zone.getRegions().stream()
                        .map(RegionMapper::toResponse)
                        .collect(Collectors.toSet());

        zoneResponseDTO.setRegions(regionResponseDTOs);

        return zoneResponseDTO;
    }

}
