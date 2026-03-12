package com.ashish.jobtracker.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    private Long companyId;
    private String position;
    private Double salaryOffered;
}
