package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.company.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentCodeUpdatedEvent extends DomainEvent
{
    private final UUID departmentId;
    private final String oldCode;
    private final String newCode;
}