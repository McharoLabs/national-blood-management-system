package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.entity.Region;

public class RegionMapper {
    public static Region toEntity(CreateRegionDTO createRegionDTO) {
        Region region = new Region();
        region.setName(createRegionDTO.getName());
        return region;
    }
}
