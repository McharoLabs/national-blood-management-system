package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.Meeting;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
    Optional<Meeting> findByScheduledAtAndCenterIdAndLocation(
            LocalDateTime scheduledAt, UUID centerId, String location
    );

    @Query("""
        SELECT DISTINCT m FROM Meeting m
        JOIN m.questionnaires q
        JOIN q.donor d
        WHERE m.id = :meetingId
          AND (:formProgressList IS NULL OR q.formProgress IN :formProgressList)
          AND (COALESCE(:donorName, '') = '' OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
    """)
    List<Meeting> findByMeetingIdAndOptionalDonorNameAndFormProgressIn(
            @Param("meetingId") UUID meetingId,
            @Param("donorName") String donorName,
            @Param("formProgressList") List<FormProgress> formProgressList
    );

    @Query("""
        SELECT m FROM Meeting m
        WHERE m.organizerId = :organizerId
          AND (COALESCE(:status, m.status) = m.status)
          AND (LOWER(m.location) LIKE LOWER(CONCAT('%', COALESCE(:location, ''), '%')))
          AND (CAST(:scheduledAt AS date) IS NULL OR m.scheduledAt = :scheduledAt)
        ORDER BY
          CASE m.status
            WHEN 'ONGOING' THEN 0
            WHEN 'PLANNED' THEN 1
            WHEN 'COMPLETED' THEN 2
            WHEN 'CANCELLED' THEN 3
            ELSE 4
          END,
          m.scheduledAt ASC
    """)
    Page<Meeting> findByOrganizerFiltered(
            @Param("organizerId") UUID organizerId,
            @Param("status") MeetingStatus status,
            @Param("location") String location,
            @Param("scheduledAt") LocalDateTime scheduledAt,
            Pageable pageable
    );


    @Query("""
        SELECT m FROM Meeting m
        JOIN m.staffs s
        WHERE s.staffId = :staffId
          AND (COALESCE(:status, m.status) = m.status)
          AND (LOWER(m.location) LIKE LOWER(CONCAT('%', COALESCE(:location, ''), '%')))
          AND (CAST(:scheduledAt AS date) IS NULL OR m.scheduledAt = :scheduledAt)
        ORDER BY
          CASE m.status
            WHEN 'ONGOING' THEN 0
            WHEN 'PLANNED' THEN 1
            WHEN 'COMPLETED' THEN 2
            WHEN 'CANCELLED' THEN 3
            ELSE 4
          END,
          m.scheduledAt ASC
    """)
    Page<Meeting> findByStaffFiltered(
            @Param("staffId") UUID staffId,
            @Param("status") MeetingStatus status,
            @Param("location") String location,
            @Param("scheduledAt") LocalDateTime scheduledAt,
            Pageable pageable
    );


    @Query("""
        SELECT DISTINCT m FROM Meeting m
        JOIN m.staffs s
        WHERE m.scheduledAt = :scheduledAt
          AND s.staffId IN :staffIds
          AND m.id <> :currentMeetingId
    """)
    List<Meeting> findMeetingsByScheduledAtAndStaffIdsExcludingMeeting(
            @Param("scheduledAt") LocalDateTime scheduledAt,
            @Param("staffIds") List<UUID> staffIds,
            @Param("currentMeetingId") UUID currentMeetingId
    );

}
