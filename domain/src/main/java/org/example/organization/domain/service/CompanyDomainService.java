package org.example.organization.domain.service;

import org.example.organization.domain.model.Company;
import org.example.organization.domain.model.CompanyInfo;
import org.example.organization.domain.repository.CompanyRepository;
import org.example.organization.domain.repository.DepartmentRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * 公司领域服务
 * 处理公司相关的领域逻辑
 */
public class CompanyDomainService
{

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    public CompanyDomainService(CompanyRepository companyRepository, DepartmentRepository departmentRepository)
    {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    // 添加根据ID查找公司的方法
    public Optional<Company> findById(UUID companyId)
    {
        return companyRepository.findById(companyId.toString());
    }

    /**
     * 创建公司
     *
     * @param name   公司名称
     * @param active 是否激活
     * @return 创建的公司实体
     */
    public Company createCompany(String name, boolean active)
    {
        if (companyRepository.existsByName(name)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        Company company = Company.create(name, active);
        companyRepository.save(company);
        return company;
    }

    /**
     * 更新公司信息
     *
     * @param companyId 公司ID
     */
    public void updateCompanyInfo(UUID companyId, CompanyInfo companyInfo)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        Company company = companyOpt.get();
        company.updateInfo(companyInfo);
        companyRepository.save(company);
    }

    /**
     * 更新公司名称
     *
     * @param companyId 公司ID
     * @param newName   新名称
     */
    public void updateCompanyName(UUID companyId, String newName)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        if (companyRepository.existsByName(newName)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        Company company = companyOpt.get();
        company.updateName(newName);
        companyRepository.save(company);
    }

    /**
     * 激活公司
     *
     * @param companyId 公司ID
     */
    public void activateCompany(UUID companyId)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        Company company = companyOpt.get();
        company.activate();
        companyRepository.save(company);
    }

    /**
     * 停用公司
     *
     * @param companyId 公司ID
     */
    public void deactivateCompany(UUID companyId)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        if (!departmentRepository.findByCompanyId(companyId.toString()).isEmpty()) {
            throw new IllegalArgumentException("公司下有部门，无法停用");
        }

        Company company = companyOpt.get();
        company.deactivate();
        companyRepository.save(company);
    }
}