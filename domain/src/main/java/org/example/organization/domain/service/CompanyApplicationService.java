package org.example.organization.domain.service;

import org.example.organization.domain.model.Company;
import org.example.organization.domain.model.CompanyInfo;

import java.util.UUID;

/**
 * 公司应用服务
 * 协调领域服务和仓储来处理公司相关的业务操作
 */
public class CompanyApplicationService
{
    private final CompanyDomainService companyDomainService;
    // 添加事件发布器
    private final DomainEventPublisher domainEventPublisher;

    public CompanyApplicationService(CompanyDomainService companyDomainService,
            DomainEventPublisher domainEventPublisher)
    {
        this.companyDomainService = companyDomainService;
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
        Company company = companyDomainService.createCompany(name, active);
        // 发布领域事件
        company.getDomainEvents().forEach(domainEventPublisher::publish);
        return company;
    }

    /**
     * 更新公司信息
     *
     * @param companyId 公司ID
     */
    public void updateCompanyInfo(UUID companyId, CompanyInfo companyInfo)
    {
        companyDomainService.updateCompanyInfo(companyId, companyInfo);
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
        companyDomainService.updateCompanyName(companyId, newName);
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
        companyDomainService.deactivateCompany(companyId);
        // 获取公司并发布事件
        companyDomainService.findById(companyId).ifPresent(company ->
                company.getDomainEvents().forEach(domainEventPublisher::publish));
    }
}