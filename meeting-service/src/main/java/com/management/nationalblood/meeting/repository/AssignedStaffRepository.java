package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.AssignedStaff;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssignedStaffRepository extends JpaRepository<AssignedStaff, UUID> {
    @Query("""
        SELECT s FROM AssignedStaff s
        JOIN s.meeting m
        WHERE m.centerId = :centerId
          AND m.status NOT IN :excludedStatuses
    """)
    List<AssignedStaff> findActiveStaffByCenterId(
            @Param("centerId") UUID centerId,
            @Param("excludedStatuses") List<MeetingStatus> excludedStatuses
    );
}
