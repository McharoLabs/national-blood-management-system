package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.entity.Zone;

public class ZoneMapper {
    public static Zone toEntity(CreateZoneDTO createZoneDTO) {
        Zone zone = new Zone();

        zone.setName(createZoneDTO.getName());

        return zone;
    }

}
