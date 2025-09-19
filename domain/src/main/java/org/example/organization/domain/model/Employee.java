package org.example.organization.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.AggregateRoot;
import org.example.organization.domain.event.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 员工实体，作为聚合根
 * 负责管理员工信息
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Employee extends AggregateRoot
{
    private final UUID id;
    private String badge;
    private String name;
    private UUID departmentId;
    private UUID companyId;
    private boolean active;
    // 添加员工信息属性，用于管理详细信息
    private EmployeeInfo employeeInfo;

    // 私有构造函数，仅供内部使用
    private Employee(UUID id, String badge, String name, UUID departmentId, UUID companyId,
            LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        super(createdAt, updatedAt);
        this.id = id;
        this.badge = badge;
        this.name = name;
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.active = true;
        // 初始化员工信息
        this.employeeInfo = new EmployeeInfo();
    }

    // 用于仓储加载的私有构造函数，不触发事件
    private Employee(UUID id, String badge, String name, UUID departmentId, UUID companyId, boolean active,
            EmployeeInfo employeeInfo, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        super(createdAt, updatedAt);
        this.id = id;
        this.badge = badge;
        this.name = name;
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.active = active;
        // 确保employeeInfo不为null
        this.employeeInfo = employeeInfo != null ? employeeInfo : new EmployeeInfo();
    }

    // 工厂方法创建员工实例
    public static Employee create(String badge, String name, UUID departmentId, UUID companyId)
    {
        // 参数验证
        if (badge == null || badge.trim().isEmpty()) {
            throw new IllegalArgumentException("员工工号不能为空");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("员工工号不能为空");
        }

        if (departmentId == null) {
            throw new IllegalArgumentException("部门ID不能为空");
        }

        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }

        // 生成唯一ID
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Employee employee = new Employee(id, badge, name, departmentId, companyId, now, now);
        // 添加领域事件
        employee.addDomainEvent(new EmployeeCreatedEvent(id, badge, name, departmentId, companyId));
        return employee;
    }

    // 仓储加载时使用的工厂方法
    public static Employee create(UUID id, String badge, String name,
            UUID departmentId, UUID companyId, boolean active,
            EmployeeInfo employeeInfo, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        return new Employee(id, badge, name, departmentId, companyId, active, employeeInfo, createdAt,
                updatedAt);
    }

    // 领域行为方法
    public void updateBadgeAndName(String badge, String name)
    {
        if (badge == null || badge.trim().isEmpty()) {
            throw new IllegalArgumentException("员工姓名不能为空");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("员工邮箱不能为空");
        }

        String oldBadge = this.badge;
        String oldName = this.name;
        this.badge = badge;
        this.name = name;
        // 添加领域事件
        this.addDomainEvent(new EmployeeUpdatedEvent(this.id, oldBadge, badge, oldName, name));
    }

    // 新增更新员工详细信息的方法
    public void updateEmployeeInfo(EmployeeInfo employeeInfo)
    {
        if (employeeInfo == null) {
            throw new IllegalArgumentException("员工信息不能为空");
        }
        EmployeeInfo oldInfo = this.employeeInfo;
        this.employeeInfo = employeeInfo;
        // 添加领域事件
        this.addDomainEvent(new EmployeeInfoUpdatedEvent(this.id, oldInfo, employeeInfo));
    }

    public void updateDepartment(UUID departmentId)
    {
        if (departmentId == null) {
            throw new IllegalArgumentException("部门ID不能为空");
        }

        UUID oldDepartmentId = this.departmentId;
        this.departmentId = departmentId;
        // 添加领域事件
        this.addDomainEvent(new EmployeeDepartmentUpdatedEvent(this.id, oldDepartmentId, departmentId));
    }

    public void activate()
    {
        if (this.active) {
            throw new IllegalStateException("员工已经处于启用状态");
        }
        this.active = true;
        // 添加领域事件
        this.addDomainEvent(new EmployeeActivatedEvent(this.id));
    }

    public void deactivate()
    {
        if (!this.active) {
            throw new IllegalStateException("员工已经处于停用状态");
        }
        this.active = false;
        // 添加领域事件
        this.addDomainEvent(new EmployeeDeactivatedEvent(this.id));
    }
}