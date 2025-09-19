package org.example.organization.domain.repository;

import org.example.organization.domain.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * 部门仓储接口，持久化需要实现该接口
 */
public interface DepartmentRepository
{
    void save(Department department);

    Optional<Department> findById(String id);

    List<Department> findByCompanyId(String companyId);

    void delete(Department department);
}
