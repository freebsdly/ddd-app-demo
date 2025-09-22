package com.example.demo.organization.infrastructure;

import com.example.demo.organization.application.EntityMapper;
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
    private final EntityMapper entityMapper;

    public CompanyRepositoryImpl(CompanyJpaRepository companyJpaRepository, EntityMapper entityMapper)
    {
        this.companyJpaRepository = companyJpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public void save(Company company)
    {
        companyJpaRepository.save(entityMapper.toJpaEntity(company));
    }

    @Override
    public Optional<Company> findById(String id)
    {
        // 根据ID查找公司
        return companyJpaRepository.findById(UUID.fromString(id)).map(entityMapper::toDomainEntity);
    }

    @Override
    public Optional<Company> findByName(String name)
    {
        // 根据名称查找公司
        return companyJpaRepository.findByName(name).map(entityMapper::toDomainEntity);
    }

    @Override
    public void delete(Company company)
    {
        companyJpaRepository.deleteById(company.getId());
    }

    @Override
    public boolean existsByName(String name)
    {
        return companyJpaRepository.findByName(name).isPresent();
    }
}
