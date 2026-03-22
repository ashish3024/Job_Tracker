package com.ashish.jobtracker.controller;

import com.ashish.jobtracker.dto.request.InterviewRoundRequest;
import com.ashish.jobtracker.dto.response.InterviewRoundResponse;
import com.ashish.jobtracker.service.InterviewRoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewRoundController {

    private final InterviewRoundService interviewRoundService;

    // Any logged-in user can view rounds for their own application
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Page<InterviewRoundResponse>> getByApplication(
            @PathVariable Long applicationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(interviewRoundService.getRoundsByApplication(applicationId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewRoundResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewRoundService.getRoundById(id));
    }

    // Admin only endpoints below
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterviewRoundResponse> create(
            @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interviewRoundService.createRound(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InterviewRoundResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.ok(interviewRoundService.updateRound(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        interviewRoundService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }
}