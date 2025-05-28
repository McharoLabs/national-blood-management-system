package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.event.StaffCenterAssignmentEvent;
import com.nbtsms.zone_service.event.StaffCenterUnassignmentEvent;

public interface CenterStaffService {
    void addStaffToCenter(StaffCenterAssignmentEvent event);
    void removeStaffFromCenter(StaffCenterUnassignmentEvent event);
}
