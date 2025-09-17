package org.example.department;

import lombok.Getter;

import java.time.LocalDateTime;
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
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 私有构造函数
    private Department(UUID id, String name, String code, UUID companyId)
    {
        this.id = id;
        this.name = name;
        this.code = code;
        this.companyId = companyId;
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
        return new Department(id, name, code, companyId);
    }

    // 领域行为
    public void updateName(String name)
    {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("部门名称不能为空");
        }
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate()
    {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate()
    {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCompanyId(UUID companyId)
    {
        if (companyId == null) {
            throw new IllegalArgumentException("公司ID不能为空");
        }
        this.companyId = companyId;
        this.updatedAt = LocalDateTime.now();
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
}