package com.management.nationalblood.notificationservice.service;

import com.management.nationalblood.notificationservice.event.DonorNotificationEvent;

public interface NotificationService {
    void appointmentNotification(DonorNotificationEvent event);
}
