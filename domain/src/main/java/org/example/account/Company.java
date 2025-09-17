package org.example.account;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

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
    private String registrationNumber;
    private String address;
    private String contactPhone;
    private String email;
    private String businessScope;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 添加部门状态检查的依赖注入点
    private final Predicate<UUID> hasActiveDepartmentsChecker;

    // 私有构造函数，仅供内部使用
    private Company(UUID id, String name, boolean active)
    {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.hasActiveDepartmentsChecker = null;
    }

    // 私有构造函数，支持依赖注入
    private Company(UUID id, String name, boolean active, Predicate<UUID> hasActiveDepartmentsChecker)
    {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.hasActiveDepartmentsChecker = hasActiveDepartmentsChecker;
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

    // 工厂方法创建公司实例（带依赖注入）
    public static Company create(String name, boolean active, Predicate<UUID> hasActiveDepartmentsChecker)
    {
        // 参数验证
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        // 生成唯一ID
        UUID id = UUID.randomUUID();

        return new Company(id, name, active, hasActiveDepartmentsChecker);
    }

    // 领域行为方法
    public void updateInfo(String address, String contactPhone, String email, String businessScope)
    {
        this.address = address;
        this.contactPhone = contactPhone;
        this.email = email;
        this.businessScope = businessScope;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateName(String name, Predicate<String> isNameUnique)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }

        // 检查名称唯一性
        if (isNameUnique != null && !isNameUnique.test(name)) {
            throw new IllegalArgumentException("公司名称必须唯一");
        }

        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate()
    {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    // 修改 deactivate 方法以添加业务规则检查
    public void deactivate(Predicate<UUID> hasActiveDepartments)
    {
        // 检查是否有活跃的部门
        if (hasActiveDepartments.test(this.id)) {
            throw new IllegalStateException("无法停用公司：存在活跃的部门，请先停用所有部门");
        }

        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    // 重载 deactivate 方法，使用内部依赖
    public void deactivate()
    {
        // 检查是否有活跃的部门
        if (this.hasActiveDepartmentsChecker != null && this.hasActiveDepartmentsChecker.test(this.id)) {
            throw new IllegalStateException("无法停用公司：存在活跃的部门，请先停用所有部门");
        }

        this.active = false;
        this.updatedAt = LocalDateTime.now();
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