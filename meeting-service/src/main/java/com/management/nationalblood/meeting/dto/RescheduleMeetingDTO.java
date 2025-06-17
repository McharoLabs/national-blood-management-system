package com.management.nationalblood.meeting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleMeetingDTO {
    @NotNull
    private LocalDateTime newScheduledAt;
}
