package org.example.organization.domain.service;

import org.example.organization.domain.model.Department;
import org.example.organization.domain.repository.CompanyRepository;
import org.example.organization.domain.repository.DepartmentRepository;
import org.example.organization.domain.repository.EmployeeRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * 部门领域服务
 * 处理部门相关的领域逻辑
 */
public class DepartmentDomainService
{

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentDomainService(DepartmentRepository departmentRepository,
            CompanyRepository companyRepository, EmployeeRepository employeeRepository)
    {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * 根据ID查找部门
     *
     * @param departmentId 部门ID
     * @return 部门实体Optional
     */
    public Optional<Department> findById(UUID departmentId)
    {
        return departmentRepository.findById(departmentId.toString());
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
        // 验证公司存在
        if (companyRepository.findById(companyId.toString()).isEmpty()) {
            throw new IllegalArgumentException("指定的公司不存在");
        }

        Department department = Department.create(name, code, companyId);
        departmentRepository.save(department);
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
        // 验证公司存在
        if (companyRepository.findById(companyId.toString()).isEmpty()) {
            throw new IllegalArgumentException("指定的公司不存在");
        }

        // 验证父部门存在
        if (parentId != null && departmentRepository.findById(parentId.toString()).isEmpty()) {
            throw new IllegalArgumentException("指定的父部门不存在");
        }

        Department department = Department.create(name, code, companyId, parentId);
        departmentRepository.save(department);
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
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        Department department = departmentOpt.get();
        department.updateName(name);
        departmentRepository.save(department);
    }

    /**
     * 更新部门代码
     *
     * @param departmentId 部门ID
     * @param code         新代码
     */
    public void updateDepartmentCode(UUID departmentId, String code)
    {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        Department department = departmentOpt.get();
        department.updateCode(code);
        departmentRepository.save(department);
    }

    /**
     * 激活部门
     *
     * @param departmentId 部门ID
     */
    public void activateDepartment(UUID departmentId)
    {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        Department department = departmentOpt.get();
        department.activate();
        departmentRepository.save(department);
    }

    /**
     * 停用部门
     *
     * @param departmentId 部门ID
     */
    public void deactivateDepartment(UUID departmentId)
    {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        // 添加校验：部门下是否有员工
        if (!employeeRepository.findByDepartmentId(departmentId.toString()).isEmpty()) {
            throw new IllegalArgumentException("部门下有员工，无法停用");
        }

        Department department = departmentOpt.get();
        department.deactivate();
        departmentRepository.save(department);
    }

    /**
     * 更新部门所属公司
     *
     * @param departmentId 部门ID
     * @param companyId    新公司ID
     */
    public void updateDepartmentCompany(UUID departmentId, UUID companyId)
    {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        // 验证公司存在
        if (companyRepository.findById(companyId.toString()).isEmpty()) {
            throw new IllegalArgumentException("指定的公司不存在");
        }

        // 验证部门下是否有员工，如果有员工则不能更换公司
        if (!employeeRepository.findByDepartmentId(departmentId.toString()).isEmpty()) {
            throw new IllegalArgumentException("部门下有员工，无法更换公司");
        }

        Department department = departmentOpt.get();
        department.updateCompanyId(companyId);
        departmentRepository.save(department);
    }

    /**
     * 更新部门父部门
     *
     * @param departmentId 部门ID
     * @param parentId     新父部门ID
     */
    public void updateDepartmentParent(UUID departmentId, UUID parentId)
    {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("部门不存在");
        }

        // 验证父部门存在
        if (parentId != null && departmentRepository.findById(parentId.toString()).isEmpty()) {
            throw new IllegalArgumentException("指定的父部门不存在");
        }

        // 验证不能将部门设置为自己的子部门
        if (parentId != null && parentId.equals(departmentId)) {
            throw new IllegalArgumentException("不能将部门设置为其自身的父部门");
        }

        Department department = departmentOpt.get();
        department.updateParentId(parentId);
        departmentRepository.save(department);
    }
}