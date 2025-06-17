package com.management.nationalblood.meeting.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class QuestionnaireFilterRequest {
    private List<UUID> centerIds;
}
