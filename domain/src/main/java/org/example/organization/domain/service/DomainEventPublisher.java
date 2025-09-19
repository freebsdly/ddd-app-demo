package org.example.organization.domain.service;

import org.example.DomainEvent;

/**
 * 领域事件发布器接口
 */
public interface DomainEventPublisher
{
    void publish(DomainEvent event);
}