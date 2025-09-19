package org.example.organization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 公司信息值对象 - DDD组合模式优化
 */
@Getter
@Value
@AllArgsConstructor
public class CompanyInfo
{
    // Getter方法
    Address address;
    String contactPhone;
    String email;
    String businessScope;

    // 默认构造函数
    public CompanyInfo()
    {
        this.address = null;
        this.contactPhone = null;
        this.email = null;
        this.businessScope = null;
    }
}