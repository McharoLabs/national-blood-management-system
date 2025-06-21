package com.management.nationalblood.meeting.scheduler;

import com.management.nationalblood.meeting.service.impl.AssessmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AssessmentScheduler {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentScheduler.class);

    private final AssessmentServiceImpl assessmentService;

    public AssessmentScheduler(AssessmentServiceImpl assessmentService) {
        this.assessmentService = assessmentService;
    }

    // Debug: Run every 2 minutes
    @Scheduled(cron = "0 */2 * * * ?")
    public void sendReadyForLabAssessment() {
        assessmentService.sendQuestionnaireCompletedEvent();
    }
}
