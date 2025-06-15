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
@Table(name = "blood_pressure_pulses")
public class BloodPressureAndPulse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "bloodPressureAndPulse")
    private Questionnaire questionnaire;

    @Column(nullable = false)
    private Integer pulse;

    @Column(nullable = false)
    private Integer bloodPressure;

    @Column(nullable = false)
    private String bpMachine;

    private UUID measuredBy;
}