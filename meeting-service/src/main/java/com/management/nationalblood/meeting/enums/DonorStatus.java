package com.management.nationalblood.meeting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonorStatus {
    ALLOWED_TO_DONATE,
    TEMPORARILY_DEFERRED,
    PERMANENTLY_DEFERRED;

}
