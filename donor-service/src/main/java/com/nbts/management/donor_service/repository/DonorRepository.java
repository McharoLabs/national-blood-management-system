package com.nbts.management.donor_service.repository;

import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.enums.Gender;
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

    List<Donor> findAllByAuthSavedFalse();

    @Query("""
        SELECT d FROM Donor d
        WHERE (COALESCE(:gender, NULL) IS NULL OR d.gender = :gender)
        AND (COALESCE(:fullName, '') = '' OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')))
    """)
    Page<Donor> searchDonors(
            @Param("gender") Gender gender,
            @Param("fullName") String fullName,
            Pageable pageable
    );

    @Query("""
        SELECT d FROM Donor d
        WHERE FUNCTION('RIGHT', d.phoneNumber, 9) = :lastNineDigits
    """)
    Optional<Donor> findByLastNinePhoneDigits(@Param("lastNineDigits") String lastNineDigits);


}
