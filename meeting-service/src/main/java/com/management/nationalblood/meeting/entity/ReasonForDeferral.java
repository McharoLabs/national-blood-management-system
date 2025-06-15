package com.management.nationalblood.meeting.entity;

import com.management.nationalblood.meeting.enums.MedicalReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class ReasonForDeferral {
    @ElementCollection(targetClass = MedicalReason.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "reason_deferral_medical", joinColumns = @JoinColumn(name = "donor_id"))
    @Column(name = "medical_reason")
    private List<MedicalReason> medicalReasons;

    private String medicalOtherReasonComment;

    @ElementCollection
    @CollectionTable(name = "reason_deferral_social", joinColumns = @JoinColumn(name = "donor_id"))
    @Column(name = "social_reason")
    private List<String> socialReasons;

    private String socialReasonComment;

}
