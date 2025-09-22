package com.example.demo.organization.infrastructure;

import com.example.demo.organization.infrastructure.entity.CompanyEntity;
import org.example.organization.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, UUID>, QuerydslPredicateExecutor<Company>
{

    Optional<CompanyEntity> findByName(String name);
}
