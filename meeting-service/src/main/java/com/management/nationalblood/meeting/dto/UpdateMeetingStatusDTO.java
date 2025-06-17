package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.MeetingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMeetingStatusDTO {
    @NotNull
    private MeetingStatus status;
}
