package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.JobApplicationRequest;
import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.repository.JobApplicationRepository;
import com.ashish.jobtracker.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplicationResponse createApplication(JobApplicationRequest request) {

        // logic will be implemented later

        return null;
    }
}