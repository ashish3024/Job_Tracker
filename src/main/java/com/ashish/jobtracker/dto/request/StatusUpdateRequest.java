package com.ashish.jobtracker.dto.request;

import com.ashish.jobtracker.entity.constant.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ApplicationStatus status;
}