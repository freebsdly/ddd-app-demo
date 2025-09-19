package org.example.organization.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.DomainEvent;
import org.example.organization.domain.model.EmployeeInfo;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EmployeeInfoUpdatedEvent extends DomainEvent
{
    private final UUID employeeId;
    private final EmployeeInfo oldInfo;
    private final EmployeeInfo newInfo;

}