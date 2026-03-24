package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.InterviewRoundRequest;
import com.ashish.jobtracker.dto.response.InterviewRoundResponse;
import com.ashish.jobtracker.entity.InterviewRound;
import com.ashish.jobtracker.entity.JobApplication;
import com.ashish.jobtracker.mapper.InterviewRoundMapper;
import com.ashish.jobtracker.repository.InterviewRoundRepository;
import com.ashish.jobtracker.repository.JobApplicationRepository;
import com.ashish.jobtracker.service.InterviewRoundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewRoundServiceImpl implements InterviewRoundService {

    private final InterviewRoundRepository interviewRoundRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public InterviewRoundResponse createRound(InterviewRoundRequest request) {

        JobApplication application = jobApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: "
                        + request.getApplicationId()));

        InterviewRound round = new InterviewRound();
        round.setJobApplication(application);
        round.setRoundName(request.getRoundName());
        round.setScheduledDate(request.getScheduledDate());
        round.setResult(request.getResult());

        return InterviewRoundMapper.toResponse(interviewRoundRepository.save(round));
    }

    @Override
    public Page<InterviewRoundResponse> getRoundsByApplication(Long applicationId, Pageable pageable) {
        // verify application exists first
        jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

        return interviewRoundRepository
                .findByJobApplicationId(applicationId, pageable)
                .map(InterviewRoundMapper::toResponse);
    }

    @Override
    public InterviewRoundResponse getRoundById(Long id) {
        InterviewRound round = interviewRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview round not found with id: " + id));
        return InterviewRoundMapper.toResponse(round);
    }

    @Override
    public InterviewRoundResponse updateRound(Long id, InterviewRoundRequest request) {
        InterviewRound round = interviewRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview round not found with id: " + id));

        JobApplication application = jobApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: "
                        + request.getApplicationId()));

        round.setJobApplication(application);
        round.setRoundName(request.getRoundName());
        round.setScheduledDate(request.getScheduledDate());
        round.setResult(request.getResult());

        return InterviewRoundMapper.toResponse(interviewRoundRepository.save(round));
    }

    @Override
    public void deleteRound(Long id) {
        log.info("Delete interview round with id: {}", id);
        InterviewRound round = interviewRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview round not found with id: " + id));
        interviewRoundRepository.delete(round);
        log.info("Deleted interview round with id: {}", id);
    }
}