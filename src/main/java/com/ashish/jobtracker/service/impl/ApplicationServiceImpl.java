package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.JobApplicationRequest;
import com.ashish.jobtracker.dto.response.JobApplicationResponse;
import com.ashish.jobtracker.entity.Company;
import com.ashish.jobtracker.entity.JobApplication;
import com.ashish.jobtracker.entity.User;
import com.ashish.jobtracker.entity.constant.ApplicationStatus;
import com.ashish.jobtracker.mapper.JobApplicationMapper;
import com.ashish.jobtracker.repository.CompanyRepository;
import com.ashish.jobtracker.repository.JobApplicationRepository;
import com.ashish.jobtracker.repository.UserRepository;
import com.ashish.jobtracker.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private CompanyRepository companyRepository;
    @Override
    public JobApplicationResponse createApplication(JobApplicationRequest request) {
          User user=getCurrentUser();
            Company company=companyRepository.findById(request.getCompanyId())
                    .orElseThrow(()->new RuntimeException("Company not found with id: "
                            + request.getCompanyId()));
        JobApplication jobApplication=new JobApplication();
        jobApplication.setUser(user);
        jobApplication.setCompany(company);
        jobApplication.setPosition(request.getPosition());
        jobApplication.setSalaryOffered(request.getSalaryOffered());
        jobApplication.setStatus(ApplicationStatus.APPLIED);
        jobApplication.setAppliedDate(request.getAppliedDate());


       JobApplication saved= jobApplicationRepository.save(jobApplication);


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

    private JobApplication findOwnedApplication(Long id) {
        User currentUser = getCurrentUser();
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (!application.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to access this application");
        }
        return application;

    }


    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}