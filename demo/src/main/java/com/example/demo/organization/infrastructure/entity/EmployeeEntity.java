package com.example.demo.organization.infrastructure.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity
{
    @Id
    @UuidGenerator
    private UUID id;

    private String badge;
    private String name;
    private UUID departmentId;
    private UUID companyId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 内嵌EmployeeInfo值对象
    @Embedded
    private EmployeeInfoEmbeddable employeeInfo;
}
