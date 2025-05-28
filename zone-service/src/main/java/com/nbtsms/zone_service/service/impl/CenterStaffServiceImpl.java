package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.entity.Center;
import com.nbtsms.zone_service.entity.Staff;
import com.nbtsms.zone_service.event.StaffCenterAssignmentEvent;
import com.nbtsms.zone_service.event.StaffCenterUnassignmentEvent;
import com.nbtsms.zone_service.mapper.CenterStaffMapper;
import com.nbtsms.zone_service.repository.CenterRepository;
import com.nbtsms.zone_service.repository.CenterStaffRepository;
import com.nbtsms.zone_service.service.CenterStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class CenterStaffServiceImpl implements CenterStaffService {
    private static final Logger logger = LoggerFactory.getLogger(CenterStaffServiceImpl.class);
    private final CenterRepository centerRepository;
    private final CenterStaffRepository staffRepository;

    public CenterStaffServiceImpl(CenterRepository centerRepository, CenterStaffRepository staffRepository) {
        this.centerRepository = centerRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public void addStaffToCenter(StaffCenterAssignmentEvent event) {
        Center center = centerRepository.findById(event.getCenterId())
                .orElse(null);

        if (center == null) {
            logger.warn("Center not found for ID: {}", event.getCenterId());
            return;
        }

        Optional<Staff> staffOpt = staffRepository.findByStaffId(event.getStaffId());

        if (staffOpt.isPresent()) {
            logger.warn("Staff with ID {} is already added to center with ID {}", event.getStaffId(), event.getCenterId());
            return;
        }

        Staff staff = CenterStaffMapper.toEntity(event);

        center.getStaffMembers().add(staff);
        staff.setCenter(center);
        centerRepository.save(center);

        logger.info("Staff {} added to center successfully to center {}", staff.getEmail(), center.getName());
    }

    @Override
    public void removeStaffFromCenter(StaffCenterUnassignmentEvent event) {
        Center center = centerRepository.findById(event.getCenterId())
                .orElse(null);

        if (center == null) {
            logger.warn("Center not found for ID: {}", event.getCenterId());
            return;
        }

        Optional<Staff> staffOpt = staffRepository.findByStaffId(event.getStaffId());

        if (staffOpt.isEmpty()) {
            logger.warn("Staff with ID {} not added to any center", event.getStaffId());
            return;
        }

        Staff staff = staffOpt.get();

        if (!center.getStaffMembers().contains(staff)) {
            logger.warn("Staff with ID {} not added to center {}", staff.getStaffId(), center.getName());
        }

        center.getStaffMembers().remove(staff);
        centerRepository.save(center);
        logger.info("Staff {} removed successfully from center {}", staff.getEmail(), center.getName());
    }
}
