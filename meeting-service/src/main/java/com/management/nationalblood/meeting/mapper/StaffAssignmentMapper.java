package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.AssignedStaffResponseDTO;
import com.management.nationalblood.meeting.entity.AssignedStaff;
import com.management.nationalblood.meeting.event.StaffAssignedEvent;

public class StaffAssignmentMapper {

    public static AssignedStaff toEntity(StaffAssignedEvent event) {
        return AssignedStaff.builder()
                .staffId(event.getStaffId())
                .firstName(event.getFirstName())
                .middleName(event.getMiddleName())
                .lastName(event.getLastName())
                .phoneNumber(event.getPhoneNumber())
                .email(event.getEmail())
                .build();
    }

    public static StaffAssignedEvent toEvent(AssignedStaff staff) {
        return StaffAssignedEvent.builder()
                .staffId(staff.getStaffId())
                .firstName(staff.getFirstName())
                .middleName(staff.getMiddleName())
                .lastName(staff.getLastName())
                .phoneNumber(staff.getPhoneNumber())
                .email(staff.getEmail())
                .meetingId(staff.getMeeting().getId())
                .build();
    }

    public static AssignedStaffResponseDTO toResponse(AssignedStaff staff) {
        return AssignedStaffResponseDTO.builder()
                .id(staff.getId())
                .staffId(staff.getStaffId())
                .firstName(staff.getFirstName())
                .middleName(staff.getMiddleName())
                .lastName(staff.getLastName())
                .email(staff.getEmail())
                .phoneNumber(staff.getPhoneNumber())
                .meetingId(staff.getMeeting().getId())
                .build();
    }
}
