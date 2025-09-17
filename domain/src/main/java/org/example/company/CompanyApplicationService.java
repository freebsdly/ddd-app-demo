package org.example.company;

import java.util.UUID;

/**
 * 公司应用服务
 * 协调领域服务和仓储来处理公司相关的业务操作
 */
public class CompanyApplicationService
{
    private final CompanyDomainService companyDomainService;
    private final CompanyChecker companyChecker;
    private final DepartmentChecker departmentChecker;
    // 添加事件发布器
    private final DomainEventPublisher domainEventPublisher;

    public CompanyApplicationService(CompanyDomainService companyDomainService,
            CompanyChecker companyChecker,
            DepartmentChecker departmentChecker,
            DomainEventPublisher domainEventPublisher)
    {
        this.companyDomainService = companyDomainService;
        this.companyChecker = companyChecker;
        this.departmentChecker = departmentChecker;
        this.domainEventPublisher = domainEventPublisher;
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
        Company company = companyDomainService.createCompany(name, active, companyChecker);
        // 发布领域事件
        company.getDomainEvents().forEach(domainEventPublisher::publish);
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
        companyDomainService.updateCompanyInfo(companyId, address, contactPhone, email, businessScope);
        // 获取公司并发布事件
        companyDomainService.findById(companyId).ifPresent(company -> 
            company.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新公司名称
     *
     * @param companyId 公司ID
     * @param newName   新名称
     */
    public void updateCompanyName(UUID companyId, String newName)
    {
        companyDomainService.updateCompanyName(companyId, newName, companyChecker);
        // 获取公司并发布事件
        companyDomainService.findById(companyId).ifPresent(company -> 
            company.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 激活公司
     *
     * @param companyId 公司ID
     */
    public void activateCompany(UUID companyId)
    {
        companyDomainService.activateCompany(companyId);
        // 获取公司并发布事件
        companyDomainService.findById(companyId).ifPresent(company -> 
            company.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 停用公司
     *
     * @param companyId 公司ID
     */
    public void deactivateCompany(UUID companyId)
    {
        companyDomainService.deactivateCompany(companyId, departmentChecker);
        // 获取公司并发布事件
        companyDomainService.findById(companyId).ifPresent(company -> 
            company.getDomainEvents().forEach(domainEventPublisher::publish));
    }
}