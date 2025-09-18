package org.example.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyCreatedEvent extends DomainEvent
{
    private final UUID companyId;
    private final String companyName;
    private final boolean active;
}