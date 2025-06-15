package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.CreateMeetingDTO;
import com.management.nationalblood.meeting.dto.MeetingDTO;
import com.management.nationalblood.meeting.entity.Meeting;
import com.management.nationalblood.meeting.enums.MeetingStatus;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MeetingMapper {
    public static MeetingDTO toResponse(Meeting meeting) {
        return MeetingDTO.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .scheduledAt(meeting.getScheduledAt())
                .centerId(meeting.getCenterId())
                .organizerId(meeting.getOrganizerId())
                .status(meeting.getStatus())
                .remarks(meeting.getRemarks())
                .createdAt(meeting.getCreatedAt())
                .updatedAt(meeting.getUpdatedAt())
                .staffs(
                        meeting
                        .getStaffs()
                        .stream()
                        .map(StaffAssignmentMapper::toResponse)
                        .collect(Collectors.toList())
                )
                .location(meeting.getLocation())
                .build();
    }

    public static Meeting toEntity(CreateMeetingDTO meetingDTO) {
        if (meetingDTO == null) {
            return null;
        }

        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setDescription(meetingDTO.getDescription());
        meeting.setScheduledAt(meetingDTO.getScheduledAt());
        meeting.setCenterId(meetingDTO.getCenterId());
        meeting.setOrganizerId(meetingDTO.getOrganizerId());
        meeting.setStatus(meetingDTO.getStatus() != null ? meetingDTO.getStatus() : MeetingStatus.PLANNED);
        meeting.setRemarks(meetingDTO.getRemarks());
        meeting.setStaffs(new ArrayList<>());
        meeting.setLocation(meetingDTO.getLocation());


        return meeting;
    }

}
