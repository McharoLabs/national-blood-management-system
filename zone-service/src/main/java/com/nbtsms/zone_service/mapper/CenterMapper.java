package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.entity.Center;

public class CenterMapper {

    public static Center toEntity(CreateCenterDTO createCenterDTO) {
        Center center = new Center();

        center.setName(createCenterDTO.getName());
        center.setAddress(createCenterDTO.getAddress());
        center.setContactPerson(createCenterDTO.getContactPerson());

        return center;
    }
}
