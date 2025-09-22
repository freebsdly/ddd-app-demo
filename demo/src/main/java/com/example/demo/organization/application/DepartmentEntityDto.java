package com.example.demo.organization.application;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DepartmentEntityDto implements Serializable
{
    UUID id;
    String name;
    String code;
    boolean active;
    UUID companyId;
    UUID parentId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}