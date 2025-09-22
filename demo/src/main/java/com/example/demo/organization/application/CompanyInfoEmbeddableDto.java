package com.example.demo.organization.application;

import lombok.Value;

import java.io.Serializable;

@Value
public class CompanyInfoEmbeddableDto implements Serializable
{
    AddressEmbeddableDto address;
    String contactPhone;
    String email;
    String businessScope;
}