package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.company.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentActivatedEvent extends DomainEvent
{
    private final UUID departmentId;
}