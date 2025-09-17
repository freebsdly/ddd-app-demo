package org.example.company;

import java.util.Optional;
import java.util.UUID;

/**
 * 公司领域服务
 * 处理公司相关的领域逻辑
 */
public class CompanyDomainService
{

    private final CompanyRepository companyRepository;

    public CompanyDomainService(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    // 添加根据ID查找公司的方法
    public Optional<Company> findById(UUID companyId)
    {
        return companyRepository.findById(companyId.toString());
    }

    /**
     * 创建公司
     *
     * @param name           公司名称
     * @param active         是否激活
     * @param companyChecker 公司检查器
     * @return 创建的公司实体
     */
    public Company createCompany(String name, boolean active, CompanyChecker companyChecker)
    {
        // 检查名称唯一性
        if (companyChecker != null && !companyChecker.isNameUnique(name)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        Company company = Company.create(name, active);
        companyRepository.save(company);
        return company;
    }

    /**
     * 更新公司信息
     *
     * @param companyId     公司ID
     * @param address       地址
     * @param contactPhone  联系电话
     * @param email         邮箱
     * @param businessScope 业务范围
     */
    public void updateCompanyInfo(UUID companyId, Address address, String contactPhone, String email,
            String businessScope)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        Company company = companyOpt.get();
        company.updateInfo(address, contactPhone, email, businessScope);
        companyRepository.save(company);
    }

    /**
     * 更新公司名称
     *
     * @param companyId      公司ID
     * @param newName        新名称
     * @param companyChecker 公司检查器
     */
    public void updateCompanyName(UUID companyId, String newName, CompanyChecker companyChecker)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        Company company = companyOpt.get();
        company.updateName(newName, companyChecker);
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
     * @param companyId         公司ID
     * @param departmentChecker 部门检查器
     */
    public void deactivateCompany(UUID companyId, DepartmentChecker departmentChecker)
    {
        Optional<Company> companyOpt = companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("公司不存在");
        }

        Company company = companyOpt.get();
        company.deactivate(departmentChecker);
        companyRepository.save(company);
    }
}