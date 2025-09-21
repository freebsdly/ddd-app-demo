package com.example.demo.organization.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class DepartmentEntity
{
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String code;
    private boolean active;
    private UUID companyId;
    private UUID parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
