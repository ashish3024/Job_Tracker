package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.InterviewRoundRequest;
import com.ashish.jobtracker.dto.response.InterviewRoundResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewRoundService {

    InterviewRoundResponse createRound(InterviewRoundRequest request);

    Page<InterviewRoundResponse> getRoundsByApplication(Long applicationId, Pageable pageable);

    InterviewRoundResponse getRoundById(Long id);

    InterviewRoundResponse updateRound(Long id, InterviewRoundRequest request);

    void deleteRound(Long id);
}