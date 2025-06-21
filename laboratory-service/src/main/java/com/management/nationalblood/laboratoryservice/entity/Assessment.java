package com.management.nationalblood.laboratoryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID questionnaireId;

    @Column(nullable = false)
    private UUID donorId;

    @Column(nullable = false)
    private UUID meetingId;

    @Column(nullable = false)
    private UUID centerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private HaematologicalTest haematologicalTest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private BloodCollectionData bloodCollectionData;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "assessment")
    private LabTestResult labTestResult;
}
