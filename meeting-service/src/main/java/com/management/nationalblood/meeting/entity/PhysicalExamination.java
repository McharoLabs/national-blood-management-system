package com.management.nationalblood.meeting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "physical_examinations")
public class PhysicalExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "physicalExamination")
    private Questionnaire questionnaire;

    @Column(nullable = false)
    private Double weightKg;

    @Column(nullable = false)
    private Double heightCm;

    @Column(nullable = false)
    private String scaleUsedToMeasure;

    private UUID measuredBy;
}
