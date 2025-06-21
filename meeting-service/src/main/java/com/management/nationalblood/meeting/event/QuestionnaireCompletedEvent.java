package com.management.nationalblood.meeting.event;

import com.management.nationalblood.meeting.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionnaireCompletedEvent {
    private UUID questionnaireId;
    private UUID donorId;
    private UUID meetingId;
    private UUID centerId;

    private HaematologicalTestResponseDTO haematologicalTest;
    private BloodCollectionDataResponseDTO bloodCollectionData;

    private String timestamp;
}
