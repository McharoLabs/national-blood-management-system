package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.AdverseEventDTO;
import com.management.nationalblood.meeting.dto.AdverseEventResponseDTO;
import com.management.nationalblood.meeting.entity.AdverseEvent;

public class AdverseEventMapper {
    public static AdverseEvent toEntity(AdverseEventDTO dto) {

        AdverseEvent adverseEvent = new AdverseEvent();
        adverseEvent.setBloodCollectionIncident(dto.getBloodCollectionIncident());
        adverseEvent.setFainted(dto.getFainted());
        adverseEvent.setAdditionalComments(dto.getAdditionalComments());
        return adverseEvent;
    }

    public static AdverseEventResponseDTO toResponse(AdverseEvent event) {
        return AdverseEventResponseDTO.builder()
                .id(event.getId())
                .bloodCollectionIncident(event.getBloodCollectionIncident())
                .fainted(event.getFainted())
                .additionalComments(event.getAdditionalComments())
                .build();
    }
}
