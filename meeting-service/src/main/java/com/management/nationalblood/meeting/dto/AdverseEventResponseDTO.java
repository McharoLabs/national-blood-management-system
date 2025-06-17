package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.FaintingLevel;
import com.management.nationalblood.meeting.enums.YesNo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdverseEventResponseDTO {

    private UUID id;

    private YesNo bloodCollectionIncident;

    private FaintingLevel fainted;

    private String additionalComments;
}
