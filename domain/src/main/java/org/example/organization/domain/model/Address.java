package org.example.organization.domain.model;

import lombok.Value;

// 创建地址值对象
@Value
public class Address
{
    String street;
    String city;
    String state;
    String zipCode;
    String country;

    // 构造函数验证
    public Address(String street, String city, String state, String zipCode, String country)
    {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("街道不能为空");
        }
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    // 添加有用的方法
    public String getFullAddress()
    {
        return String.format("%s, %s, %s %s, %s", street, city, state, zipCode, country);
    }
}
