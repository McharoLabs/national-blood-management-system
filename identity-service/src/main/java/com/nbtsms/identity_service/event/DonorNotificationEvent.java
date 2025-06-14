package com.nbtsms.identity_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorNotificationEvent {
    private String phoneNumber;
    private String message;
    private String url;
}
