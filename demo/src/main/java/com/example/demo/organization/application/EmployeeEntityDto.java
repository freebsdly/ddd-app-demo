package com.example.demo.organization.application;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EmployeeEntityDto implements Serializable
{
    UUID id;
    String badge;
    String name;
    UUID departmentId;
    UUID companyId;
    boolean active;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    EmployeeInfoEmbeddableDto employeeInfo;
}