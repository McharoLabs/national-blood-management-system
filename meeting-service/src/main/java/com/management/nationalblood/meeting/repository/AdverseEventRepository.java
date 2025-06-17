package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.AdverseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdverseEventRepository extends JpaRepository<AdverseEvent, UUID> {
}
