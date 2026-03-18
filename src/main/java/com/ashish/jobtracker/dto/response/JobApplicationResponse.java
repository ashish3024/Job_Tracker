package com.ashish.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String position;
    private String status;
    private Double salaryOffered;
    private LocalDate appliedDate;
    private LocalDateTime createdAt;
}
