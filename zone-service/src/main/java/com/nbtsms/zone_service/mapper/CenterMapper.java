package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.entity.Center;

public class CenterMapper {

    public static Center toEntity(CreateCenterDTO createCenterDTO) {
        Center center = new Center();

        center.setName(createCenterDTO.getName());
        center.setAddress(createCenterDTO.getAddress());

        return center;
    }

    public static CenterResponseDTO toResponse(Center center) {
        CenterResponseDTO centerResponseDTO = new CenterResponseDTO();

        centerResponseDTO.setId(center.getId());
        centerResponseDTO.setName(center.getName());
        centerResponseDTO.setAddress(center.getAddress());

        centerResponseDTO.setRegion(RegionMapper.toResponse(center.getRegion()));


        return centerResponseDTO;
    }
}
