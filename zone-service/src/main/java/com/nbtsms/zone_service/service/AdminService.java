package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.event.AdminZoneAssignmentEvent;
import com.nbtsms.zone_service.event.AdminZoneUnassignmentEvent;

public interface AdminService {
    void addAdminToZone(AdminZoneAssignmentEvent event);
    void removeAdminFromZone(AdminZoneUnassignmentEvent event);
}
