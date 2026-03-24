package com.ashish.jobtracker.service.impl;

import com.ashish.jobtracker.dto.request.CompanyRequest;
import com.ashish.jobtracker.dto.response.CompanyResponse;
import com.ashish.jobtracker.entity.Company;
import com.ashish.jobtracker.mapper.CompanyMapper;
import com.ashish.jobtracker.repository.CompanyRepository;
import com.ashish.jobtracker.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        log.info("Create company request: {}", request.getName());
        Company company = new Company();
        company.setName(request.getName());
        company.setLocation(request.getLocation());
        log.info("Company created: {}", request.getName());
        return CompanyMapper.toResponse(companyRepository.save(company));

    }

    @Override
    public Page<CompanyResponse> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(CompanyMapper::toResponse);
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
        log.info("Update company request: {}", request.getName());
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        company.setName(request.getName());
        company.setLocation(request.getLocation());
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {
        log.info("Delete company with id: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        companyRepository.delete(company);
    }
}