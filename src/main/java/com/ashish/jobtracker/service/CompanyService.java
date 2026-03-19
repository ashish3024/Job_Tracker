package com.ashish.jobtracker.service;

import com.ashish.jobtracker.dto.request.CompanyRequest;
import com.ashish.jobtracker.dto.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyResponse createCompany(CompanyRequest request);

    Page<CompanyResponse> getAllCompanies(Pageable pageable);

    CompanyResponse getCompanyById(Long id);

    CompanyResponse updateCompany(Long id, CompanyRequest request);

    void deleteCompany(Long id);
}