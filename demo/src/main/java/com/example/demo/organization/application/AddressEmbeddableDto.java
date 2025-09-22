package com.example.demo.organization.application;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressEmbeddableDto implements Serializable
{
    String street;
    String city;
    String state;
    String zipCode;
    String country;
}