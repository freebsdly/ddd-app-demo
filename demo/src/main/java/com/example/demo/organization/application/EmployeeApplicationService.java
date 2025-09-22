package com.example.demo.organization.application;

import org.example.organization.domain.model.Employee;
import org.example.organization.domain.service.DomainEventPublisher;
import org.example.organization.domain.service.EmployeeDomainService;

import java.util.UUID;

/**
 * 员工应用服务
 * 协调领域服务和仓储来处理员工相关的业务操作
 */
public class EmployeeApplicationService
{
    private final EmployeeDomainService employeeDomainService;
    private final DomainEventPublisher domainEventPublisher;

    public EmployeeApplicationService(EmployeeDomainService employeeDomainService,
            DomainEventPublisher domainEventPublisher)
    {
        this.employeeDomainService = employeeDomainService;
        this.domainEventPublisher = domainEventPublisher;
    }

    /**
     * 创建员工
     *
     * @return 创建的员工实体
     */
    public Employee createEmployee(String badge, String name, UUID departmentId, UUID companyId)
    {
        Employee employee = employeeDomainService.createEmployee(badge, name, departmentId, companyId);
        // 发布领域事件
        employee.getDomainEvents().forEach(domainEventPublisher::publish);
        return employee;
    }

    /**
     * 更新员工工号和姓名
     */
    public void updateEmployeeBadgeAndName(UUID employeeId, String badge, String name)
    {
        employeeDomainService.updateEmployeeBadgeAndName(employeeId, badge, name);
        // 获取员工并发布事件
        employeeDomainService.findById(employeeId).ifPresent(employee ->
                employee.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新员工详细信息
     */
    public void updateEmployeeInfo(UUID employeeId, org.example.organization.domain.model.EmployeeInfo employeeInfo)
    {
        employeeDomainService.updateEmployeeInfo(employeeId, employeeInfo);
        // 获取员工并发布事件
        employeeDomainService.findById(employeeId).ifPresent(employee ->
                employee.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新员工部门
     */
    public void updateEmployeeDepartment(UUID employeeId, UUID departmentId)
    {
        employeeDomainService.updateEmployeeDepartment(employeeId, departmentId);
        // 获取员工并发布事件
        employeeDomainService.findById(employeeId).ifPresent(employee ->
                employee.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 激活员工
     */
    public void activateEmployee(UUID employeeId)
    {
        employeeDomainService.activateEmployee(employeeId);
        // 获取员工并发布事件
        employeeDomainService.findById(employeeId).ifPresent(employee ->
                employee.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 停用员工
     */
    public void deactivateEmployee(UUID employeeId)
    {
        employeeDomainService.deactivateEmployee(employeeId);
        // 获取员工并发布事件
        employeeDomainService.findById(employeeId).ifPresent(employee ->
                employee.getDomainEvents().forEach(domainEventPublisher::publish));
    }
}