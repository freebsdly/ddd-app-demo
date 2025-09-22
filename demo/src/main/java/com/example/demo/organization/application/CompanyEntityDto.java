package com.example.demo.organization.application;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CompanyEntityDto implements Serializable
{
    UUID id;
    String name;
    boolean active;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    CompanyInfoEmbeddableDto companyInfo;
}