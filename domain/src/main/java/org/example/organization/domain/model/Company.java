package org.example.organization.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.AggregateRoot;
import org.example.organization.domain.event.*;

import java.time.LocalDateTime;
import java.util.Objects;
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

    // 私有构造函数，仅供内部使用
    private Company(UUID id, String name, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.active = active;
        // 初始化公司信息
        this.companyInfo = new CompanyInfo();
    }

    // 用于仓储加载的私有构造函数，不触发事件
    private Company(UUID id, String name, CompanyInfo companyInfo, boolean active,
            LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.companyInfo = Objects.requireNonNullElseGet(companyInfo, CompanyInfo::new);
        this.active = active;
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
        LocalDateTime now = LocalDateTime.now();
        Company company = new Company(id, name, active, now, now);
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
    public void updateInfo(CompanyInfo companyInfo)
    {
        // 创建新的公司信息对象
        this.companyInfo = companyInfo;
        // 添加领域事件
        this.addDomainEvent(new CompanyInfoUpdatedEvent(this.id, this.name));
    }

    public void updateName(String name)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        String oldName = this.name;
        this.name = name;
        // 添加领域事件
        this.addDomainEvent(new CompanyNameUpdatedEvent(this.id, oldName, name));
    }

    public void activate()
    {
        if (this.active) {
            throw new IllegalStateException("公司已经处于启用状态");
        }
        this.active = true;
        // 添加领域事件
        this.addDomainEvent(new CompanyActivatedEvent(this.id, this.name));
    }

    // 修改 deactivate 方法以添加业务规则检查
    public void deactivate()
    {
        // 添加业务规则检查
        if (!active) {
            throw new IllegalStateException("公司已经处于停用状态");
        }
        this.active = false;
        // 添加领域事件
        this.addDomainEvent(new CompanyDeactivatedEvent(this.id, this.name));
    }

}