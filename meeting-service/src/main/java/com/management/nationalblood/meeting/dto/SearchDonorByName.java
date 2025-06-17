package com.management.nationalblood.meeting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchDonorByName {
    @NotBlank
    private String donorName;
}
