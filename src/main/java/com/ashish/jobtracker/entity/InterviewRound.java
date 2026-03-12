package com.ashish.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "interview_rounds")
public class InterviewRound extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private JobApplication jobApplication;

    @Column(nullable = false)
    private String roundName;

    private LocalDate scheduledDate;

    private String result;
}