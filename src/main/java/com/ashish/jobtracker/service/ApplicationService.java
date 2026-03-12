package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.JobApplicationRequest;
import com.ashish.jobtracker.dto.response.JobApplicationResponse;

public interface ApplicationService {

    JobApplicationResponse createApplication(JobApplicationRequest request);

}