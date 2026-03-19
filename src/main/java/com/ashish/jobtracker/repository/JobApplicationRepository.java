package com.ashish.jobtracker.repository;

import com.ashish.jobtracker.entity.JobApplication;
import com.ashish.jobtracker.entity.constant.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByUserIdAndStatus(Long id, ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByUserId(Long id, Pageable pageable);
}