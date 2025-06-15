package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "meetings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"scheduledAt", "centerId", "location"})
        }
)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Questionnaire> questionnaires;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private UUID centerId;

    @Column(nullable = false)
    private UUID organizerId;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignedStaff> staffs = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingStatus status;

    private String remarks;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

        if (status == null) {
            status = MeetingStatus.PLANNED;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (scheduledAt.isBefore(todayStart)) {
            throw new BadRequestException(Map.of("scheduledAt", "Meeting cannot be scheduled in the past."));
        }
    }

    public void addStaff(AssignedStaff assignedStaff) {
        // Check if this staff is already assigned to this meeting
        boolean alreadyAssigned = staffs.stream()
                .anyMatch(s -> s.getStaffId().equals(assignedStaff.getStaffId()));
        if (!alreadyAssigned) {
            assignedStaff.setMeeting(this);
            staffs.add(assignedStaff);
        }
    }

    public boolean isStaffDoubleBooked(UUID staffId, List<Meeting> otherMeetings) {
        for (Meeting m : otherMeetings) {
            if (m.getScheduledAt().isEqual(this.scheduledAt)) {
                boolean isAssigned = m.getStaffs().stream()
                        .anyMatch(s -> s.getStaffId().equals(staffId));
                if (isAssigned) {
                    return true;
                }
            }
        }
        return false;
    }

}
