package com.example.demo.organization.infrastructure;

import lombok.AllArgsConstructor;
import org.example.organization.domain.model.Employee;
import org.example.organization.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class EmployeeRepositoryImpl implements EmployeeRepository
{
    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public void save(Employee employee)
    {

    }

    @Override
    public Optional<Employee> findById(String id)
    {
        return Optional.empty();
    }

    @Override
    public List<Employee> findByDepartmentId(String departmentId)
    {
        return List.of();
    }

    @Override
    public List<Employee> findByCompanyId(String companyId)
    {
        return List.of();
    }

    @Override
    public void delete(Employee employee)
    {

    }

    @Override
    public boolean existsByEmail(String email)
    {
        return false;
    }
}
