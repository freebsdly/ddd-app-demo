package com.example.demo;

import org.example.DomainEvent;
import org.example.organization.domain.repository.CompanyRepository;
import org.example.organization.domain.repository.DepartmentRepository;
import org.example.organization.domain.repository.EmployeeRepository;
import org.example.organization.domain.service.CompanyDomainService;
import org.example.organization.domain.service.DepartmentDomainService;
import org.example.organization.domain.service.DomainEventPublisher;
import org.example.organization.domain.service.EmployeeDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config
{


    @Bean
    public CompanyDomainService companyDomainService(CompanyRepository companyRepository,
            DepartmentRepository departmentRepository)
    {
        return new CompanyDomainService(companyRepository, departmentRepository);
    }

    @Bean
    public DomainEventPublisher domainEventPublisher()
    {
        return new DomainEventPublisher()
        {
            @Override
            public void publish(DomainEvent event)
            {

            }
        };
    }

    @Bean
    public DepartmentDomainService departmentDomainService(DepartmentRepository departmentRepository,
            CompanyRepository companyRepository, EmployeeRepository employeeRepository)
    {
        return new DepartmentDomainService(departmentRepository, companyRepository, employeeRepository);
    }

    @Bean
    public EmployeeDomainService employeeDomainService(EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository, CompanyRepository companyRepository)
    {
        return new EmployeeDomainService(employeeRepository, departmentRepository, companyRepository);
    }
}
