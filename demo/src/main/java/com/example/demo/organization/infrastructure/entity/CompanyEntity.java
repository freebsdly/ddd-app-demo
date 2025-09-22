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
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity
{
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 内嵌CompanyInfo值对象
    @Embedded
    private CompanyInfoEmbeddable companyInfo;
}