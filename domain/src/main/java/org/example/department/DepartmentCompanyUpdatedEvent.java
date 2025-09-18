package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentCompanyUpdatedEvent extends DomainEvent
{
    private final UUID departmentId;
    private final UUID oldCompanyId;
    private final UUID newCompanyId;
}