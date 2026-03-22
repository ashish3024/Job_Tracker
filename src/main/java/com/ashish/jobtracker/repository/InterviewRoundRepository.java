package com.ashish.jobtracker.repository;

import com.ashish.jobtracker.entity.InterviewRound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {

    Page<InterviewRound> findByJobApplicationId(Long applicationId, Pageable pageable);
}