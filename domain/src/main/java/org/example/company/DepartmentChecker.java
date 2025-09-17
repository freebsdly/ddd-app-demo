package org.example.company;

import java.util.UUID;

// 新增业务规则接口
public interface DepartmentChecker
{
    boolean hasActiveDepartments(UUID companyId);
}
