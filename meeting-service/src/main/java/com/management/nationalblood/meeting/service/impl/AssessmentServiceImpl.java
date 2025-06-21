package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.constants.KafkaTopics;
import com.management.nationalblood.meeting.dto.BloodCollectionDataResponseDTO;
import com.management.nationalblood.meeting.dto.CreateAssessmentDTO;
import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.dto.HaematologicalTestResponseDTO;
import com.management.nationalblood.meeting.entity.Donor;
import com.management.nationalblood.meeting.entity.Meeting;
import com.management.nationalblood.meeting.entity.Questionnaire;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.event.QuestionnaireCompletedEvent;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import com.management.nationalblood.meeting.mapper.BloodCollectionDataMapper;
import com.management.nationalblood.meeting.mapper.DonorMapper;
import com.management.nationalblood.meeting.mapper.AssessmentMapper;
import com.management.nationalblood.meeting.mapper.HaematologicalTestMapper;
import com.management.nationalblood.meeting.repository.DonorRepository;
import com.management.nationalblood.meeting.repository.MeetingRepository;
import com.management.nationalblood.meeting.repository.QuestionnaireRepository;
import com.management.nationalblood.meeting.service.AssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AssessmentServiceImpl implements AssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImpl.class);
    private final MeetingRepository meetingRepository;
    private final DonorRepository donorRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AssessmentServiceImpl(MeetingRepository meetingRepository, DonorRepository donorRepository, QuestionnaireRepository questionnaireRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.meetingRepository = meetingRepository;
        this.donorRepository = donorRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public UUID initializeQuestionnaire(CreateAssessmentDTO questionnaireDTO) throws BadRequestException, NotFoundException {
        Meeting meeting= meetingRepository.findById(questionnaireDTO.getMeetingId())
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Meeting not found")));

        if (meeting.getStatus() != MeetingStatus.ONGOING) {
            throw new BadRequestException(Map.of("detail", "Meeting to initialize the assessment must be ONGOING"));
        }

        Donor donor = donorRepository.findByDonorId(questionnaireDTO.getDonorId())
                .orElse(null);

        if (donor == null) {
            donor = DonorMapper.toEntity(questionnaireDTO);
            donorRepository.save(donor);
            logger.info("New donor record with ID {} added to assessment records", donor.getDonorId());
        }

        Questionnaire latestForm = questionnaireRepository
                .findFirstByDonorAndFormProgressNotOrderByFormStartedAtDesc(donor, FormProgress.COMPLETED)
                .orElse(null);

        if (latestForm != null) {
            if (latestForm.isFormIncomplete()) {
                throw new BadRequestException(Map.of("detail", "Donor has an incomplete assessment."));
            }

            if (!latestForm.isEligibleForNewDonation()) {
                throw new BadRequestException(Map.of("detail", "Donor is not eligible to donate until 3 months have passed since the last donation."));
            }
        }

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setDonor(donor);
        questionnaire.setMeeting(meeting);

        Questionnaire saved = questionnaireRepository.save(questionnaire);
        return saved.getId();
    }


    @Override
    public AssessmentResponseDTO getQuestionnaire(UUID donorId) throws NotFoundException, BadRequestException {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "No assessment for this donor")));

        Questionnaire questionnaire = questionnaireRepository.findByDonor(donor)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "No assessment found")));

        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public Page<AssessmentResponseDTO> getQuestionnairesByCenterIds(
            List<UUID> centerIds,
            Pageable pageable,
            FormProgress formProgress,
            String donorName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime meetingStartDate,
            LocalDateTime meetingEndDate
    ) throws NotFoundException {
        return questionnaireRepository.findAllByCenterIdsWithFilters(
                centerIds, formProgress, donorName, startDate, endDate,
                meetingStartDate, meetingEndDate,
                pageable
        ).map(AssessmentMapper::toResponse);
    }

    @Override
    public Page<AssessmentResponseDTO> getQuestionnairesByCenter(
            UUID centerId,
            Pageable pageable,
            FormProgress formProgress,
            String donorName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime meetingStartDate,
            LocalDateTime meetingEndDate
    ) throws NotFoundException {
        return questionnaireRepository.findAllByCenterIdWithFilters(
                centerId, formProgress, donorName, startDate, endDate,
                meetingStartDate, meetingEndDate,
                pageable
        ).map(AssessmentMapper::toResponse);
    }

    @Override
    public Page<AssessmentResponseDTO> getQuestionnairesByMeeting(
            UUID meetingId,
            Pageable pageable,
            FormProgress formProgress,
            String donorName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime meetingStartDate,
            LocalDateTime meetingEndDate
    ) throws NotFoundException, BadRequestException {
        return questionnaireRepository.findAllByMeetingIdWithFilters(
                meetingId, formProgress, donorName, startDate, endDate,
                meetingStartDate, meetingEndDate,
                pageable
        ).map(AssessmentMapper::toResponse);
    }

    @Override
    public AssessmentResponseDTO getDonorLastQuestionnaire(UUID donorId) throws NotFoundException, BadRequestException {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new NotFoundException(Map.of("donorId", "No assessment record for this donor")));

        Questionnaire questionnaire = questionnaireRepository.findFirstByDonorOrderByFormStartedAtDesc(donor)
                .orElseThrow(() -> new NotFoundException(Map.of("donorId", "No previous questionnaire found")));

        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public Page<AssessmentResponseDTO> getDonorQuestionnaires(UUID donorId, Pageable pageable) {
        return questionnaireRepository
                .findAllByDonorIdOrderByFormStartedAtDesc(donorId, pageable)
                .map(AssessmentMapper::toResponse);
    }

    @Override
    public AssessmentResponseDTO getQuestionnaireById(UUID questionnaireId) throws NotFoundException {
        return AssessmentMapper.toResponse(questionnaireRepository.findById(questionnaireId).orElseThrow(() -> new NotFoundException(Map.of("detail", "Assessment not found"))));
    }

    @Override
    public void sendQuestionnaireCompletedEvent() throws NotFoundException, BadRequestException {
        List<Questionnaire> questionnaires = questionnaireRepository.findAllCompletedNotSubmittedToLab(FormProgress.COMPLETED);

        if (!questionnaires.isEmpty()) {
            for (Questionnaire q : questionnaires) {
                HaematologicalTestResponseDTO haematologicalTestDTO =
                        HaematologicalTestMapper.toResponse(q.getHaematologicalTest());
                BloodCollectionDataResponseDTO bloodCollectionDataDTO =
                        BloodCollectionDataMapper.toResponse(q.getBloodCollectionData());

                QuestionnaireCompletedEvent event = QuestionnaireCompletedEvent.builder()
                        .questionnaireId(q.getId())
                        .donorId(q.getDonor().getDonorId())
                        .meetingId(q.getMeeting().getId())
                        .centerId(q.getMeeting().getCenterId())
                        .haematologicalTest(haematologicalTestDTO)
                        .bloodCollectionData(bloodCollectionDataDTO)
                        .timestamp(LocalDateTime.now().toString())
                        .build();

                kafkaTemplate.send(KafkaTopics.ASSESSMENT_READY_FOR_LAB, event);
                q.setSubmittedToLab(true);
                questionnaireRepository.save(q);
            }
        }
    }

    @Override
    public Page<AssessmentResponseDTO> getQuestionnairesByCenterIdsAndFormStartedAtRange(List<UUID> centerIds, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return questionnaireRepository.findByCenterIdsAndFormStartedAtRange(centerIds, startDate, endDate, pageable)
                .map(AssessmentMapper::toResponse);
    }

}
