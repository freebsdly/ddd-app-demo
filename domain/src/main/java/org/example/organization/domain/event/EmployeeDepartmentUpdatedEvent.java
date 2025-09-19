package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EmployeeDepartmentUpdatedEvent extends DomainEvent {
    private final UUID employeeId;
    private final UUID oldDepartmentId;
    private final UUID newDepartmentId;
}