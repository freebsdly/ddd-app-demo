package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyDeactivatedEvent extends DomainEvent
{
    private final UUID companyId;
    private final String companyName;

}