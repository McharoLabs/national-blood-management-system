package com.management.nationalblood.meeting.entity;

import com.nbtsms.national_blood_management_system.questionnaire.enums.DonorStatus;
import com.nbtsms.national_blood_management_system.user.entity.User;
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
@Table(name = "final_evaluations")
public class FinalDonorEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "finalDonorEvaluation")
    private Questionnaire questionnaire;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DonorStatus donorStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "medicalOtherReasonComment", column = @Column(nullable = true)),
            @AttributeOverride(name = "socialReasonComment", column = @Column(nullable = true))
    })
    private ReasonForDeferral reasonForDeferral;


    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User counselor;
}