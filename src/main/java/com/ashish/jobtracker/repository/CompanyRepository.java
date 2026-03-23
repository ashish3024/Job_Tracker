package com.ashish.jobtracker.repository;

import com.ashish.jobtracker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}