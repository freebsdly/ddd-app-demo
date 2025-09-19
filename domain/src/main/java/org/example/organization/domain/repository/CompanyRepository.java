package org.example.organization.domain.repository;

import org.example.organization.domain.model.Company;

import java.util.Optional;

/**
 * 公司仓储接口，持续化需要实现该接口
 */
public interface CompanyRepository
{
    void save(Company company);

    Optional<Company> findById(String id);

    Optional<Company> findByName(String name);

    void delete(Company company);

    boolean existsByName(String name);
}