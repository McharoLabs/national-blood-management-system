package com.nbtsms.identity_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorAuthCreatedEvent {
    private String phoneNumber;
    private boolean authSaved;
}
