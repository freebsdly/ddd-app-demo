package org.example.company;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 公司实体，同时也是聚合根
 * 负责管理公司信息
 */
@Getter
public class Company
{
    // 唯一标识符
    private final UUID id;

    // 领域属性
    private String name;
    private Address address;
    private String contactPhone;
    private String email;
    private String businessScope;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 领域事件列表
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // 私有构造函数，仅供内部使用
    private Company(UUID id, String name, boolean active)
    {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

        return new Company(id, name, active);
    }

    // 领域行为方法
    public void updateInfo(Address address, String contactPhone, String email, String businessScope)
    {
        this.address = address;
        this.contactPhone = contactPhone;
        this.email = email;
        this.businessScope = businessScope;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.domainEvents.add(new CompanyInfoUpdatedEvent(this.id, this.name));
    }

    // 修改 updateName 方法，使用 CompanyChecker 替代 Predicate
    public void updateName(String name, CompanyChecker companyChecker)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        // 检查名称唯一性
        if (companyChecker != null && companyChecker.isNameUnique(name)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        String oldName = this.name;
        this.name = name;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.domainEvents.add(new CompanyNameUpdatedEvent(this.id, oldName, name));
    }

    public void activate()
    {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.domainEvents.add(new CompanyActivatedEvent(this.id, this.name));
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
        this.domainEvents.add(new CompanyDeactivatedEvent(this.id, this.name));
    }

    // 获取领域事件并清空
    public List<DomainEvent> getDomainEvents()
    {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    // 基于ID的相等性比较
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
