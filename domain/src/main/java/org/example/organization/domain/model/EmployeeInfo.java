package org.example.organization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 员工信息值对象
 * 用于封装员工的详细信息
 */
@Getter
@Value
@AllArgsConstructor
public class EmployeeInfo {
    Address address;
    String phoneNumber;
    String emergencyContactName;
    String emergencyContactPhone;
    
    // 默认构造函数
    public EmployeeInfo() {
        this.address = null;
        this.phoneNumber = null;
        this.emergencyContactName = null;
        this.emergencyContactPhone = null;
    }
}