package com.ashish.jobtracker.mapper;

import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.entity.JobApplication;

public class JobApplicationMapper {
    public static JobApplicationResponse toJobApplicationResponse(JobApplication jobApplication) {
        JobApplicationResponse jobApplicationResponse = new JobApplicationResponse();
        jobApplicationResponse.setId(jobApplication.getId());
        jobApplicationResponse.setCompanyName(jobApplication.getCompany().getName());
        jobApplicationResponse.setPosition(jobApplication.getPosition());
        jobApplicationResponse.setStatus(jobApplication.getStatus().name());
        return jobApplicationResponse;
    }
}
