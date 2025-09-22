package com.example.demo.organization.application;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeInfoEmbeddableDto implements Serializable
{
    AddressEmbeddableDto address;
    String phoneNumber;
    String emergencyContactName;
    String emergencyContactPhone;
}