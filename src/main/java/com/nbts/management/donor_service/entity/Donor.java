package com.nbts.management.donor_service.entity;

import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.enums.MaritalStatus;
import com.nbts.management.donor_service.enums.PolygamousMarriage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "donors")
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolygamousMarriage polygamousMarriage;

    @Column(nullable = true)
    private Integer numberOfSpouses;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String Region;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String ward;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String educationLevel;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = true)
    private String lastDonation;

    @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Questionnaire> questionnaires;

}
