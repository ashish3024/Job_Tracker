package com.ashish.jobtracker.mapper;

import com.ashish.jobtracker.dto.response.CompanyResponse;
import com.ashish.jobtracker.entity.Company;

public class CompanyMapper {
    public static CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setLocation(company.getLocation());
        return companyResponse;

    }
}
