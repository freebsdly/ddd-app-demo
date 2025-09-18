package org.example.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 公司实体，同时也是聚合根
 * 负责管理公司信息
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Company extends AggregateRoot
{
    // 唯一标识符
    private final UUID id;

    // 领域属性
    private String name;
    // 使用组合方式管理公司信息
    private CompanyInfo companyInfo;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 私有构造函数，仅供内部使用
    private Company(UUID id, String name, boolean active)
    {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        // 初始化公司信息
        this.companyInfo = new CompanyInfo();
    }

    // 用于仓储加载的私有构造函数，不触发事件
    private Company(UUID id, String name, CompanyInfo companyInfo, boolean active,
            LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        this.id = id;
        this.name = name;
        this.companyInfo = companyInfo;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 工厂方法创建公司实例
    public static Company create(String name, boolean active)
    {
        // 参数验证
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        // 生成唯一ID
        UUID id = UUID.randomUUID();

        Company company = new Company(id, name, active);
        // 添加领域事件
        company.addDomainEvent(new CompanyCreatedEvent(id, name, active));
        return company;
    }

    // 仓储加载时使用的工厂方法
    public static Company create(UUID id, String name, CompanyInfo companyInfo,
            boolean active, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        return new Company(id, name, companyInfo, active, createdAt, updatedAt);
    }

    // 领域行为方法
    public void updateInfo(Address address, String contactPhone, String email, String businessScope)
    {
        // 创建新的公司信息对象
        this.companyInfo = new CompanyInfo(address, contactPhone, email, businessScope);
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new CompanyInfoUpdatedEvent(this.id, this.name));
    }

    public void updateName(String name, CompanyChecker companyChecker)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        // 检查名称唯一性
        if (companyChecker != null && !companyChecker.isNameUnique(name)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        String oldName = this.name;
        this.name = name;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new CompanyNameUpdatedEvent(this.id, oldName, name));
    }

    public void activate()
    {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new CompanyActivatedEvent(this.id, this.name));
    }

    // 修改 deactivate 方法以添加业务规则检查
    public void deactivate(DepartmentChecker checker)
    {
        // 检查是否有活跃的部门
        if (checker.hasActiveDepartments(this.id)) {
            throw new IllegalStateException("无法停用公司：存在活跃的部门，请先停用所有部门");
        }

        this.active = false;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new CompanyDeactivatedEvent(this.id, this.name));
    }

}