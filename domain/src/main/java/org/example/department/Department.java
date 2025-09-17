package org.example.department;

import lombok.Getter;
import org.example.company.DomainEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 部门实体，独立的聚合根
 */
@Getter
public class Department
{
    // Getters
    private final UUID id;
    private String name;
    private String code;
    private boolean active;
    private UUID companyId;
    private UUID parentId; // 添加父部门ID支持树形结构
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 领域事件列表
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // 私有构造函数
    private Department(UUID id, String name, String code, UUID companyId, UUID parentId)
    {
        this.id = id;
        this.name = name;
        this.code = code;
        this.companyId = companyId;
        this.parentId = parentId; // 初始化父部门ID
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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
        Department department = new Department(id, name, code, companyId, null); // 默认无父部门
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
        Department department = new Department(id, name, code, companyId, parentId);
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
        this.updatedAt = LocalDateTime.now();
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
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentCodeUpdatedEvent(this.id, oldCode, code));
    }

    public void activate()
    {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentActivatedEvent(this.id));
    }

    public void deactivate()
    {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentDeactivatedEvent(this.id));
    }

    public void updateCompanyId(UUID companyId)
    {
        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }
        UUID oldCompanyId = this.companyId;
        this.companyId = companyId;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentCompanyUpdatedEvent(this.id, oldCompanyId, companyId));
    }

    // 添加删除方法
    public void remove()
    {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentDeactivatedEvent(this.id));
    }

    // 添加设置父部门的方法
    public void setParentId(UUID parentId)
    {
        UUID oldParentId = this.parentId;
        this.parentId = parentId;
        this.updatedAt = LocalDateTime.now();
        // 添加领域事件
        this.addDomainEvent(new DepartmentParentUpdatedEvent(this.id, oldParentId, parentId));
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
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    // 添加领域事件的辅助方法
    private void addDomainEvent(DomainEvent event)
    {
        this.domainEvents.add(event);
    }
}