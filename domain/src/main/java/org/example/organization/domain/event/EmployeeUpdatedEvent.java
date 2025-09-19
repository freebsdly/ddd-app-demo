package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EmployeeUpdatedEvent extends DomainEvent
{
    private final UUID employeeId;
    private final String oldBadge;
    private final String newBadge;
    private final String oldName;
    private final String newName;
}