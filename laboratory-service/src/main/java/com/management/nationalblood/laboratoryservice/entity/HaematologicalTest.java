package com.management.nationalblood.laboratoryservice.entity;

import com.management.nationalblood.laboratoryservice.enums.SerumProteinStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "haematological_tests")
public class HaematologicalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Assessment assessment;


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
