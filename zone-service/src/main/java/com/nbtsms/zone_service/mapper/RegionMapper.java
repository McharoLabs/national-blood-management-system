package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.entity.Region;


public class RegionMapper {
    public static Region toEntity(CreateRegionDTO createRegionDTO) {
        Region region = new Region();
        region.setName(createRegionDTO.getName());
        return region;
    }

    public static RegionResponseDTO toResponse(Region region) {
        RegionResponseDTO regionResponseDTO = new RegionResponseDTO();

        regionResponseDTO.setId(region.getId());
        regionResponseDTO.setName(region.getName());
        regionResponseDTO.setTotalCenter(region.getCenters().size());

        return regionResponseDTO;
    }
}
