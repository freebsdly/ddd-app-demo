package com.example.demo.organization.infrastructure;

import com.example.demo.organization.infrastructure.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentJpaRepository
        extends JpaRepository<DepartmentEntity, UUID>, QuerydslPredicateExecutor<DepartmentEntity>
{
    List<DepartmentEntity> findByCompanyId(UUID companyId);
}