package com.ashish.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobApplicationRequest {

    @NotNull(message ="CompanyId is required")
    private Long companyId;
    @NotBlank(message = "Position is required")
    private String position;

    @Positive(message = "Salary must be a positive number")
    private Double salaryOffered;

    private LocalDate appliedDate;
}
