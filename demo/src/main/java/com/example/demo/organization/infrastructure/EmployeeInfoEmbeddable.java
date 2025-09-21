package com.example.demo.organization.infrastructure;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class EmployeeInfoEmbeddable
{
    @Embedded
    private AddressEmbeddable address;
    private String phoneNumber;
    private String emergencyContactName;
    private String emergencyContactPhone;

}
