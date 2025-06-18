package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.Donor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonorRepository extends JpaRepository<Donor, UUID> {
    Optional<Donor> findByFullNameIgnoreCase(String fullName);

    Optional<Donor> findByPhoneNumber(String phoneNumber);

    Optional<Donor> findByDonorId(UUID donorId);

    @Query("""
        SELECT d FROM Donor d
        WHERE (:fullName IS NULL OR d.fullName LIKE %:fullName%)
    """)
    Page<Donor> findAllByOptionalFullName(@Param("fullName") String fullName, Pageable pageable);

    @Query("""
        SELECT d FROM Donor d
        WHERE LOWER(d.fullName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Donor> searchDonorByFullName(@Param("name") String name);


    @NonNull
    @Override
    Page<Donor> findAll(@NonNull Pageable pageable);
}
