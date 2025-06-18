package com.nbts.management.donor_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorNotificationEvent {
    private String phoneNumber;
    private String message;
    private String url;
}
