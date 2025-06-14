package com.nbtsms.identity_service.service;

import com.nbtsms.identity_service.event.DonorAuthCreatedEvent;

public interface DonorAuthService {
    void createDonorAuth(DonorAuthCreatedEvent donorAuthCreatedEvent);
}
