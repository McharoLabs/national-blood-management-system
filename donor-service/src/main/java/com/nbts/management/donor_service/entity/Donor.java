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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column()
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column()
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column()
    private PolygamousMarriage polygamousMarriage;

    @Column()
    private Integer numberOfSpouses;

    @Column()
    private LocalDate dateOfBirth;

    @Column()
    private Integer age;

    @Column()
    private String nationality;

    @Column(unique = true)
    private String phoneNumber;

    @Column()
    private String region;

    @Column()
    private String district;

    @Column()
    private String ward;

    @Column()
    private String street;

    @Column()
    private String address;

    @Column()
    private String educationLevel;

    @Column()
    private String occupation;

    @Column()
    private LocalDateTime lastDonation;

    @Column(nullable = false)
    private boolean authSaved = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
