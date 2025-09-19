package org.example.organization.domain.repository;

import org.example.organization.domain.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * 员工仓储接口，持久化需要实现该接口
 */
public interface EmployeeRepository
{
    void save(Employee employee);

    Optional<Employee> findById(String id);

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByCompanyId(String companyId);

    void delete(Employee employee);

    boolean existsByEmail(String email);
}