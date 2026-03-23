package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.JobApplicationRequest;
import com.ashish.jobtracker.dto.request.StatusUpdateRequest;
import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.entity.Company;
import com.ashish.jobtracker.entity.JobApplication;
import com.ashish.jobtracker.entity.User;
import com.ashish.jobtracker.entity.constant.ApplicationStatus;
import com.ashish.jobtracker.mapper.JobApplicationMapper;
import com.ashish.jobtracker.messaging.StatusChangeProducer;
import com.ashish.jobtracker.model.StatusChangedMessage;
import com.ashish.jobtracker.repository.CompanyRepository;
import com.ashish.jobtracker.repository.JobApplicationRepository;
import com.ashish.jobtracker.repository.UserRepository;
import com.ashish.jobtracker.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final StatusChangeProducer statusChangeProducer;

    @Override
    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        User currentUser = getCurrentUser();

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        JobApplication application = new JobApplication();
        application.setUser(currentUser);
        application.setCompany(company);
        application.setPosition(request.getPosition());
        application.setSalaryOffered(request.getSalaryOffered());
        application.setAppliedDate(request.getAppliedDate());
        application.setStatus(ApplicationStatus.APPLIED);

        JobApplication saved = jobApplicationRepository.save(application);
        return JobApplicationMapper.toJobApplicationResponse(saved);
    }

    @Override
    public Page<JobApplicationResponse> getMyApplications(ApplicationStatus status, Pageable pageable) {
        User currentUser = getCurrentUser();

        Page<JobApplication> applications = (status != null)
                ? jobApplicationRepository.findByUserIdAndStatus(currentUser.getId(), status, pageable)
                : jobApplicationRepository.findByUserId(currentUser.getId(), pageable);

        return applications.map(JobApplicationMapper::toJobApplicationResponse);
    }

    @Override
    public JobApplicationResponse getApplicationById(Long id) {
        JobApplication application = findOwnedApplication(id);
        return JobApplicationMapper.toJobApplicationResponse(application);
    }

    @Override
    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {
        JobApplication application = findOwnedApplication(id);

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        application.setCompany(company);
        application.setPosition(request.getPosition());
        application.setSalaryOffered(request.getSalaryOffered());
        application.setAppliedDate(request.getAppliedDate());

        JobApplication updated = jobApplicationRepository.save(application);
        return JobApplicationMapper.toJobApplicationResponse(updated);
    }

    @Override
    public void deleteApplication(Long id) {
        JobApplication application = findOwnedApplication(id);
        jobApplicationRepository.delete(application);
    }

    @Override
    public JobApplicationResponse updateStatus(Long id, StatusUpdateRequest request) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        String oldStatus = application.getStatus().name();
        application.setStatus(request.getStatus());
        JobApplication updated = jobApplicationRepository.save(application);

        StatusChangedMessage message = new StatusChangedMessage(
                updated.getId(),
                updated.getUser().getEmail(),
                updated.getUser().getName(),
                updated.getCompany().getName(),
                updated.getPosition(),
                oldStatus,
                updated.getStatus().name()
        );
        statusChangeProducer.sendStatusChange(message);

        return JobApplicationMapper.toJobApplicationResponse(updated);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    private JobApplication findOwnedApplication(Long id) {
        User currentUser = getCurrentUser();
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (!application.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to access this application");
        }
        return application;
    }
}