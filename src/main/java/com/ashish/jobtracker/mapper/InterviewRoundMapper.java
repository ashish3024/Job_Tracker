package com.ashish.jobtracker.mapper;

import com.ashish.jobtracker.dto.response.InterviewRoundResponse;
import com.ashish.jobtracker.entity.InterviewRound;

public class InterviewRoundMapper {

    public static InterviewRoundResponse toResponse(InterviewRound round) {
        InterviewRoundResponse response = new InterviewRoundResponse();
        response.setId(round.getId());
        response.setApplicationId(round.getJobApplication().getId());
        response.setCompanyName(round.getJobApplication().getCompany().getName());
        response.setPosition(round.getJobApplication().getPosition());
        response.setRoundName(round.getRoundName());
        response.setScheduledDate(round.getScheduledDate());
        response.setResult(round.getResult());
        return response;
    }
}