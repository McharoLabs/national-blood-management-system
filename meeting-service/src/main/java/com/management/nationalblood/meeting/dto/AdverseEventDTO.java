package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.FaintingLevel;
import com.management.nationalblood.meeting.enums.YesNo;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdverseEventDTO {
    @NotNull private YesNo bloodCollectionIncident;
    private FaintingLevel fainted;
    private String additionalComments;
    private UUID questionnaireId;
}
