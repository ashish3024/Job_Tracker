package com.ashish.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String position;
    private String status;
}
