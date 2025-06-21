package com.management.nationalblood.laboratoryservice.kafka;

import com.management.nationalblood.laboratoryservice.constants.KafkaTopics;
import com.management.nationalblood.laboratoryservice.event.QuestionnaireCompletedEvent;
import com.management.nationalblood.laboratoryservice.service.impl.AssessmentServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireCompletedListener {
    private final AssessmentServiceImpl assessmentService;

    public QuestionnaireCompletedListener(AssessmentServiceImpl assessmentService) {
        this.assessmentService = assessmentService;
    }

    @KafkaListener(topics = KafkaTopics.ASSESSMENT_READY_FOR_LAB)
    public void handleQuestionnaireCompletedForTest(QuestionnaireCompletedEvent event) {
        assessmentService.handleCompletedAssessmentForTest(event);
    }
}
