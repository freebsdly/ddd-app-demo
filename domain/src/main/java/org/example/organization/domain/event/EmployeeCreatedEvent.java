package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EmployeeCreatedEvent extends DomainEvent
{
    private final UUID employeeId;
    private final String badge;
    private final String name;
    private final UUID departmentId;
    private final UUID companyId;
}