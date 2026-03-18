package com.ashish.jobtracker.mapper;

import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.entity.JobApplication;

public class JobApplicationMapper {

    public static JobApplicationResponse toJobApplicationResponse(JobApplication jobApplication) {
        JobApplicationResponse response = new JobApplicationResponse();
        response.setId(jobApplication.getId());
        response.setCompanyName(jobApplication.getCompany().getName());
        response.setPosition(jobApplication.getPosition());
        response.setStatus(jobApplication.getStatus().name());
        response.setSalaryOffered(jobApplication.getSalaryOffered());
        response.setAppliedDate(jobApplication.getAppliedDate());
        response.setCreatedAt(jobApplication.getCreatedAt());
        return response;
    }
}