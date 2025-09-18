package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentNameUpdatedEvent extends DomainEvent
{
    private final UUID departmentId;
    private final String oldName;
    private final String newName;
}