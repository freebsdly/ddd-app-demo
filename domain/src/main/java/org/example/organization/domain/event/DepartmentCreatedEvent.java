package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DepartmentCreatedEvent extends DomainEvent
{
    private final UUID departmentId;
    private final String name;
    private final String code;
    private final UUID companyId;
}