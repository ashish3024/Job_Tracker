package com.ashish.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InterviewRoundResponse {

    private Long id;
    private Long applicationId;
    private String companyName;
    private String position;
    private String roundName;
    private LocalDate scheduledDate;
    private String result;
}