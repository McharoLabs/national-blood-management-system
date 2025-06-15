package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.BloodProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "blood_collection_data")
public class BloodCollectionData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "bloodCollectionData")
    private Questionnaire questionnaire;

    @Column(nullable = false)
    private LocalTime timeNeedleInserted;

    @Column(nullable = false)
    private LocalTime timeNeedleRemoved;
    @Column(nullable = false)
    private boolean venipunctureSuccessful;

    @Column(nullable = false)
    private boolean bloodCollectionUnsuccessful;

    @Column(nullable = false)
    private String smallAmountCollected;

    @Column(nullable = false)
    private String quantityOfBloodCollected;

    @Column(nullable = false)
    private String scaleUsed;

    @Column(nullable = false)
    private String apheresisMachineUsed;

    @Column(nullable = false)
    private String bloodBagType;

    @Column(nullable = false)
    private String bloodBagLotNumber;

    @Column(nullable = false)
    private LocalDate bloodBagExpiryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodProductType bloodProductType;

    private UUID finalizedBy;
}