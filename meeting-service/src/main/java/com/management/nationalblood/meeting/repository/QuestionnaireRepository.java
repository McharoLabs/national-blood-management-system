package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.Donor;
import com.management.nationalblood.meeting.entity.Meeting;
import com.management.nationalblood.meeting.entity.Questionnaire;
import com.management.nationalblood.meeting.enums.FormProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {

    Optional<Questionnaire> findFirstByDonorAndFormProgressNotOrderByFormStartedAtDesc(Donor donor, FormProgress formProgress);
    Optional<Questionnaire> findByDonor(Donor donor);
    Optional<Questionnaire> findFirstByDonorOrderByFormStartedAtDesc(Donor donor);
    Optional<Questionnaire> findByMeeting(Meeting meeting);
    Optional<Questionnaire> findByIdAndFormProgress(UUID id, FormProgress formProgress);
    Optional<Questionnaire> findByMeetingAndFormProgress(Meeting meeting, FormProgress formProgress);

    @Query("""
        SELECT q FROM Questionnaire q
        JOIN q.donor d
        WHERE q.meeting.id = :meetingId
          AND (:formProgressList IS NULL OR q.formProgress IN :formProgressList)
          AND (COALESCE(:donorName, '') = '' OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
    """)
    List<Questionnaire> findByMeetingIdAndFormProgressInAndOptionalDonorName(
            @Param("meetingId") UUID meetingId,
            @Param("donorName") String donorName,
            @Param("formProgressList") List<FormProgress> formProgressList
    );

    @Query("""
        SELECT q FROM Questionnaire q
        WHERE q.meeting.id = :meetingId
          AND LOWER(q.donor.fullName) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
          AND (COALESCE(:status, q.formProgress) = q.formProgress)
    """)
    List<Questionnaire> findAllByMeetingIdAndOptionalDonorNameAndStatus(
            @Param("meetingId") UUID meetingId,
            @Param("name") String name,
            @Param("status") FormProgress status
    );

    @Query("""
        SELECT q FROM Questionnaire q
        WHERE q.donor.id = :donorId
        ORDER BY q.formStartedAt DESC
    """)
    Page<Questionnaire> findAllByDonorIdOrderByFormStartedAtDesc(
            @Param("donorId") UUID donorId,
            Pageable pageable
    );

    @Query("""
        SELECT q FROM Questionnaire q
        JOIN q.meeting m
        WHERE m.centerId IN :centerIds
          AND (:formProgress IS NULL OR q.formProgress = :formProgress)
          AND (COALESCE(:donorName, '') = '' OR LOWER(q.donor.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
          AND (:startDate IS NULL OR q.formStartedAt >= :startDate)
          AND (:endDate IS NULL OR q.formStartedAt <= :endDate)
          AND (:meetingStartDate IS NULL OR m.scheduledAt >= :meetingStartDate)
          AND (:meetingEndDate IS NULL OR m.scheduledAt <= :meetingEndDate)
        ORDER BY q.formStartedAt DESC
    """)
    Page<Questionnaire> findAllByCenterIdsWithFilters(
            @Param("centerIds") List<UUID> centerIds,
            @Param("formProgress") FormProgress formProgress,
            @Param("donorName") String donorName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("meetingStartDate") LocalDateTime meetingStartDate,
            @Param("meetingEndDate") LocalDateTime meetingEndDate,
            Pageable pageable
    );

    @Query("""
        SELECT q FROM Questionnaire q
        JOIN q.meeting m
        WHERE m.id = :meetingId
          AND (:formProgress IS NULL OR q.formProgress = :formProgress)
          AND (COALESCE(:donorName, '') = '' OR LOWER(q.donor.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
          AND (:startDate IS NULL OR q.formStartedAt >= :startDate)
          AND (:endDate IS NULL OR q.formStartedAt <= :endDate)
          AND (:meetingStartDate IS NULL OR m.scheduledAt >= :meetingStartDate)
          AND (:meetingEndDate IS NULL OR m.scheduledAt <= :meetingEndDate)
        ORDER BY q.formStartedAt DESC
    """)
    Page<Questionnaire> findAllByMeetingIdWithFilters(
            @Param("meetingId") UUID meetingId,
            @Param("formProgress") FormProgress formProgress,
            @Param("donorName") String donorName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("meetingStartDate") LocalDateTime meetingStartDate,
            @Param("meetingEndDate") LocalDateTime meetingEndDate,
            Pageable pageable
    );

    @Query("""
        SELECT q FROM Questionnaire q
        JOIN q.meeting m
        WHERE m.centerId = :centerId
          AND (:formProgress IS NULL OR q.formProgress = :formProgress)
          AND (COALESCE(:donorName, '') = '' OR LOWER(q.donor.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
          AND (:startDate IS NULL OR q.formStartedAt >= :startDate)
          AND (:endDate IS NULL OR q.formStartedAt <= :endDate)
          AND (:meetingStartDate IS NULL OR m.scheduledAt >= :meetingStartDate)
          AND (:meetingEndDate IS NULL OR m.scheduledAt <= :meetingEndDate)
        ORDER BY q.formStartedAt DESC
    """)
    Page<Questionnaire> findAllByCenterIdWithFilters(
            @Param("centerId") UUID centerId,
            @Param("formProgress") FormProgress formProgress,
            @Param("donorName") String donorName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("meetingStartDate") LocalDateTime meetingStartDate,
            @Param("meetingEndDate") LocalDateTime meetingEndDate,
            Pageable pageable
    );

    @Query("""
        SELECT q FROM Questionnaire q
        JOIN q.donor d
        WHERE q.meeting.id = :meetingId
          AND q.formProgress = :formProgress
          AND (COALESCE(:donorName, '') = '' OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :donorName, '%')))
    """)
    List<Questionnaire> findByMeetingIdAndFormProgressAndOptionalDonorName(
            @Param("meetingId") UUID meetingId,
            @Param("formProgress") FormProgress formProgress,
            @Param("donorName") String donorName
    );
}
