package com.example.demo.organization.infrastructure;

import com.example.demo.organization.infrastructure.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeJpaRepository
        extends JpaRepository<EmployeeEntity, UUID>, QuerydslPredicateExecutor<EmployeeEntity>
{
}