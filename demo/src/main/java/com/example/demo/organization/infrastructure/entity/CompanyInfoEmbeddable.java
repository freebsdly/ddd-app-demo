package com.example.demo.organization.infrastructure.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class CompanyInfoEmbeddable
{
    // Getters and Setters
    @Embedded
    private AddressEmbeddable address;
    private String contactPhone;
    private String email;
    private String businessScope;

}
