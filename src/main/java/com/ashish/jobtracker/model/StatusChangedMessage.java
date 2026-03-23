package com.ashish.jobtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangedMessage implements Serializable {

    private Long applicationId;
    private String applicantEmail;
    private String applicantName;
    private String companyName;
    private String position;
    private String oldStatus;
    private String newStatus;
}