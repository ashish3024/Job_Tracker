package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.JobApplicationRequest;
import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.entity.constant.ApplicationStatus;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    JobApplicationResponse createApplication(JobApplicationRequest request);
    Page<JobApplicationResponse> getMyApplications(ApplicationStatus status, Pageable pageable);
    JobApplicationResponse getApplicationById(Long id);
    JobApplicationResponse updateApplication(Long id, JobApplicationRequest request);

    void deleteApplication(Long id);
}