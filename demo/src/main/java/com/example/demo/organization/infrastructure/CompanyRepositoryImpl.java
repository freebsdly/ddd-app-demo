package com.example.demo.organization.infrastructure;

import org.example.organization.domain.model.Company;
import org.example.organization.domain.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * 公司仓储接口实现
 */
@Component
public class CompanyRepositoryImpl implements CompanyRepository
{

    private final CompanyJpaRepository companyJpaRepository;

    public CompanyRepositoryImpl(CompanyJpaRepository companyJpaRepository)
    {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    public void save(Company company)
    {
        companyJpaRepository.save(company);
    }

    @Override
    public Optional<Company> findById(String id)
    {
        // 根据ID查找公司
        return companyJpaRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Company> findByName(String name)
    {
        // 根据名称查找公司
        return companyJpaRepository.findByName(name);
    }

    @Override
    public void delete(Company company)
    {
        companyJpaRepository.delete(company);
    }

    @Override
    public boolean existsByName(String name)
    {
        return companyJpaRepository.findByName(name).isPresent();
    }
}
