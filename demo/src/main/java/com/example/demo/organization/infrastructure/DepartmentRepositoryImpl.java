package com.example.demo.organization.infrastructure;

import com.example.demo.organization.application.EntityMapper;
import com.example.demo.organization.infrastructure.entity.DepartmentEntity;
import lombok.AllArgsConstructor;
import org.example.organization.domain.model.Department;
import org.example.organization.domain.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class DepartmentRepositoryImpl implements DepartmentRepository
{

    private final DepartmentJpaRepository departmentJpaRepository;
    private final EntityMapper entityMapper;

    @Override
    public void save(Department department)
    {
        DepartmentEntity jpaEntity = entityMapper.toJpaEntity(department);
        departmentJpaRepository.save(jpaEntity);
    }

    @Override
    public Optional<Department> findById(String id)
    {
        Optional<DepartmentEntity> byId = departmentJpaRepository.findById(UUID.fromString(id));
        return byId.map(entityMapper::toDomainEntity);
    }

    @Override
    public List<Department> findByCompanyId(String companyId)
    {
        List<DepartmentEntity> byCompanyId = departmentJpaRepository.findByCompanyId(UUID.fromString(companyId));
        return byCompanyId.stream().map(entityMapper::toDomainEntity).toList();
    }

    @Override
    public void delete(Department department)
    {
        departmentJpaRepository.delete(entityMapper.toJpaEntity(department));
    }
}
