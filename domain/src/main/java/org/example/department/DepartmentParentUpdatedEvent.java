package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentParentUpdatedEvent extends DomainEvent
{
    private final UUID departmentId;
    private final UUID oldParentId;
    private final UUID newParentId;
}