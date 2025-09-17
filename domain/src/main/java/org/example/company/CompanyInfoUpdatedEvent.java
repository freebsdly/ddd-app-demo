package org.example.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyInfoUpdatedEvent extends DomainEvent
{
    private final UUID companyId;
    private final String companyName;
}