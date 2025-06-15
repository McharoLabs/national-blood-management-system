package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.SerumProteinStatus;
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
@Table(name = "haematological_tests")
public class HaematologicalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "haematologicalTest")
    private Questionnaire questionnaire;

    @Column(nullable = false)
    private Double haemoglobinLevel;

    @Column(nullable = false)
    private Double haematocrit;

    @Column(nullable = false)
    private Integer plateletsCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SerumProteinStatus serumProteinStatus;

    private UUID measuredBy;
}
