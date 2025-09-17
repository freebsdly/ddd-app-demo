package org.example.company;

/**
 * 领域事件发布器接口
 */
public interface DomainEventPublisher
{
    void publish(DomainEvent event);
}