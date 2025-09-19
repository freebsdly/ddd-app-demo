package org.example.organization.domain.service;

import org.example.organization.domain.model.Department;

import java.util.UUID;

/**
 * 部门应用服务
 * 协调领域服务和仓储来处理部门相关的业务操作
 */
public class DepartmentApplicationService
{

    private final DepartmentDomainService departmentDomainService;
    private final DomainEventPublisher domainEventPublisher;

    public DepartmentApplicationService(DepartmentDomainService departmentDomainService,
            DomainEventPublisher domainEventPublisher)
    {
        this.departmentDomainService = departmentDomainService;
        this.domainEventPublisher = domainEventPublisher;
    }

    /**
     * 创建部门
     *
     * @param name      部门名称
     * @param code      部门代码
     * @param companyId 公司ID
     * @return 创建的部门实体
     */
    public Department createDepartment(String name, String code, UUID companyId)
    {
        Department department = departmentDomainService.createDepartment(name, code, companyId);
        // 发布领域事件
        department.getDomainEvents().forEach(domainEventPublisher::publish);
        return department;
    }

    /**
     * 创建带父部门的部门
     *
     * @param name      部门名称
     * @param code      部门代码
     * @param companyId 公司ID
     * @param parentId  父部门ID
     * @return 创建的部门实体
     */
    public Department createDepartment(String name, String code, UUID companyId, UUID parentId)
    {
        Department department = departmentDomainService.createDepartment(name, code, companyId, parentId);
        // 发布领域事件
        department.getDomainEvents().forEach(domainEventPublisher::publish);
        return department;
    }

    /**
     * 更新部门名称
     *
     * @param departmentId 部门ID
     * @param name         新名称
     */
    public void updateDepartmentName(UUID departmentId, String name)
    {
        departmentDomainService.updateDepartmentName(departmentId, name);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新部门代码
     *
     * @param departmentId 部门ID
     * @param code         新代码
     */
    public void updateDepartmentCode(UUID departmentId, String code)
    {
        departmentDomainService.updateDepartmentCode(departmentId, code);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 激活部门
     *
     * @param departmentId 部门ID
     */
    public void activateDepartment(UUID departmentId)
    {
        departmentDomainService.activateDepartment(departmentId);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 停用部门
     *
     * @param departmentId 部门ID
     */
    public void deactivateDepartment(UUID departmentId)
    {
        departmentDomainService.deactivateDepartment(departmentId);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新部门所属公司
     *
     * @param departmentId 部门ID
     * @param companyId    新公司ID
     */
    public void updateDepartmentCompany(UUID departmentId, UUID companyId)
    {
        departmentDomainService.updateDepartmentCompany(departmentId, companyId);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }

    /**
     * 更新部门父部门
     *
     * @param departmentId 部门ID
     * @param parentId     新父部门ID
     */
    public void updateDepartmentParent(UUID departmentId, UUID parentId)
    {
        departmentDomainService.updateDepartmentParent(departmentId, parentId);
        // 获取部门并发布事件
        departmentDomainService.findById(departmentId).ifPresent(department ->
                department.getDomainEvents().forEach(domainEventPublisher::publish));
    }
}