package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.service.DonorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class DonorServiceImpl implements DonorService {


}
