package org.example.organization.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.AggregateRoot;
import org.example.organization.domain.event.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 部门实体，独立的聚合根
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Department extends AggregateRoot
{
    // Getters
    private final UUID id;
    private String name;
    private String code;
    private boolean active;
    private UUID companyId;
    private UUID parentId;

    // 私有构造函数
    private Department(UUID id, String name, String code, UUID companyId, UUID parentId, LocalDateTime createdAt,
            LocalDateTime updatedAt)
    {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.code = code;
        this.companyId = companyId;
        this.parentId = parentId; // 初始化父部门ID
        this.active = true;
    }

    // 工厂方法
    public static Department create(String name, String code, UUID companyId)
    {
        // 参数验证
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("部门名称不能为空");
        }

        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("部门代码不能为空");
        }

        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Department department = new Department(id, name, code, companyId, null, now, now); // 默认无父部门
        // 添加领域事件
        department.addDomainEvent(new DepartmentCreatedEvent(id, name, code, companyId));
        return department;
    }

    // 支持指定父部门的创建方法
    public static Department create(String name, String code, UUID companyId, UUID parentId)
    {
        // 参数验证
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("部门名称不能为空");
        }

        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("部门代码不能为空");
        }

        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }

        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Department department = new Department(id, name, code, companyId, parentId, now, now);
        // 添加领域事件
        department.addDomainEvent(new DepartmentCreatedEvent(id, name, code, companyId));
        return department;
    }

    // 领域行为
    public void updateName(String name)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("部门名称不能为空");
        }
        String oldName = this.name;
        this.name = name;
        // 添加领域事件
        this.addDomainEvent(new DepartmentNameUpdatedEvent(this.id, oldName, name));
    }

    // 添加更新部门代码的方法
    public void updateCode(String code)
    {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("部门代码不能为空");
        }
        String oldCode = this.code;
        this.code = code;
        // 添加领域事件
        this.addDomainEvent(new DepartmentCodeUpdatedEvent(this.id, oldCode, code));
    }

    public void activate()
    {
        if (this.active) {
            throw new IllegalStateException("部门已经处于启用状态");
        }
        this.active = true;
        // 添加领域事件
        this.addDomainEvent(new DepartmentActivatedEvent(this.id));
    }

    public void deactivate()
    {
        if (!this.active) {
            throw new IllegalStateException("部门已经处于禁用状态");
        }
        this.active = false;
        // 添加领域事件
        this.addDomainEvent(new DepartmentDeactivatedEvent(this.id));
    }

    // 添加删除方法
    public void remove()
    {
        this.active = false;
        // 添加领域事件
        this.addDomainEvent(new DepartmentDeactivatedEvent(this.id));
    }

    // 添加设置父部门的方法
    public void updateParentId(UUID parentId)
    {
        UUID oldParentId = this.parentId;
        this.parentId = parentId;
        // 添加领域事件
        this.addDomainEvent(new DepartmentParentUpdatedEvent(this.id, oldParentId, parentId));
    }

    public void updateCompanyId(UUID companyId)
    {
        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }
        UUID oldCompanyId = this.companyId;
        this.companyId = companyId;
        // 添加领域事件
        this.addDomainEvent(new DepartmentCompanyUpdatedEvent(this.id, oldCompanyId, companyId));
    }
}