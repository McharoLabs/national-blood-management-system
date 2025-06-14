package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.constant.KafkaTopics;
import com.nbtsms.identity_service.entity.DonorAuth;
import com.nbtsms.identity_service.event.DonorAuthCreatedEvent;
import com.nbtsms.identity_service.event.DonorNotificationEvent;
import com.nbtsms.identity_service.repository.DonorAuthRepository;
import com.nbtsms.identity_service.service.DonorAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class DonorAuthServiceImpl implements DonorAuthService {
    private static final Logger logger = LoggerFactory.getLogger(DonorAuthServiceImpl.class);
    private final DonorAuthRepository donorAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DonorAuthServiceImpl(DonorAuthRepository donorAuthRepository, PasswordEncoder passwordEncoder, KafkaTemplate<String, Object> kafkaTemplate) {
        this.donorAuthRepository = donorAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createDonorAuth(DonorAuthCreatedEvent donorAuthCreatedEvent) {
        DonorAuth existingDonorAuth = donorAuthRepository.findByPhoneNumber(donorAuthCreatedEvent.getPhoneNumber())
                .orElse(null);

        if (existingDonorAuth == null) {
            DonorAuth donorAuth = new DonorAuth();
            donorAuth.setPhoneNumber(donorAuthCreatedEvent.getPhoneNumber());
            donorAuth.setPassword(passwordEncoder.encode("12345"));

            donorAuthRepository.save(donorAuth);
            logger.info("Donor with phone number {} saved successfully", donorAuthCreatedEvent.getPhoneNumber());

            kafkaTemplate.send(KafkaTopics.SAVE_DONOR_AUTH, new DonorAuthCreatedEvent(
                    donorAuth.getPhoneNumber(),
                    true
            ));

            kafkaTemplate.send(
                    KafkaTopics.DONOR_NOTIFICATION,
                    new DonorNotificationEvent(
                            donorAuthCreatedEvent.getPhoneNumber(),
                            String.format(
                                    "Umefanikiwa kufungua akaunti Damu Salama. Username yako ni: %s, password yako ni: 12345. Tembelea: %s kutazama taarifa zako.",
                                    donorAuthCreatedEvent.getPhoneNumber(),
                                    "https://nbts.go.tz/profile"
                            ),
                            ""
                    )
            );

        }

    }
}
