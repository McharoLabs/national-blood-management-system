package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.FaintingLevel;
import com.management.nationalblood.meeting.enums.YesNo;
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
@Table(name = "adverse_events")
public class AdverseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "adverseEvent")
    private Questionnaire questionnaire;

    @Enumerated(EnumType.STRING)
    private YesNo bloodCollectionIncident;

    @Enumerated(EnumType.STRING)
    private FaintingLevel fainted;

    private String additionalComments;
}