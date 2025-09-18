package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合根基类，提供领域事件管理功能
 */
public abstract class AggregateRoot
{
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * 获取并清空领域事件列表
     *
     * @return 领域事件列表
     */
    public List<DomainEvent> getDomainEvents()
    {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    /**
     * 添加领域事件
     *
     * @param event 领域事件
     */
    protected void addDomainEvent(DomainEvent event)
    {
        this.domainEvents.add(event);
    }
}