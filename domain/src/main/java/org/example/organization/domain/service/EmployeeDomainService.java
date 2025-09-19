package org.example.organization.domain.service;

import org.example.organization.domain.model.Employee;
import org.example.organization.domain.model.EmployeeInfo;
import org.example.organization.domain.repository.CompanyRepository;
import org.example.organization.domain.repository.DepartmentRepository;
import org.example.organization.domain.repository.EmployeeRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * 员工领域服务
 * 处理员工相关的领域逻辑
 */
public class EmployeeDomainService
{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public EmployeeDomainService(EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            CompanyRepository companyRepository)
    {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    // 添加根据ID查找员工的方法
    public Optional<Employee> findById(UUID employeeId)
    {
        return employeeRepository.findById(employeeId.toString());
    }

    /**
     * 创建员工
     *
     * @return 创建的员工实体
     */
    public Employee createEmployee(String badge, String name, UUID departmentId, UUID companyId)
    {
        // 验证部门存在
        Optional<org.example.organization.domain.model.Department> departmentOpt = 
            departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("指定的部门不存在");
        }

        // 验证公司存在
        Optional<org.example.organization.domain.model.Company> companyOpt = 
            companyRepository.findById(companyId.toString());
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("指定的公司不存在");
        }

        // 验证部门是否属于该公司
        if (!departmentOpt.get().getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("指定的部门不属于该公司");
        }

        Employee employee = Employee.create(badge, name, departmentId, companyId);
        employeeRepository.save(employee);
        return employee;
    }

    /**
     * 更新员工工号和姓名
     */
    public void updateEmployeeBadgeAndName(UUID employeeId, String badge, String name)
    {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId.toString());
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("员工不存在");
        }

        Employee employee = employeeOpt.get();
        employee.updateBadgeAndName(badge, name);
        employeeRepository.save(employee);
    }

    /**
     * 更新员工详细信息
     */
    public void updateEmployeeInfo(UUID employeeId, EmployeeInfo employeeInfo)
    {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId.toString());
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("员工不存在");
        }

        Employee employee = employeeOpt.get();
        employee.updateEmployeeInfo(employeeInfo);
        employeeRepository.save(employee);
    }

    /**
     * 更新员工部门
     */
    public void updateEmployeeDepartment(UUID employeeId, UUID departmentId)
    {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId.toString());
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("员工不存在");
        }

        // 验证部门存在
        Optional<org.example.organization.domain.model.Department> departmentOpt = 
            departmentRepository.findById(departmentId.toString());
        if (departmentOpt.isEmpty()) {
            throw new IllegalArgumentException("指定的部门不存在");
        }

        Employee employee = employeeOpt.get();
        
        // 验证新部门是否与员工当前公司匹配
        if (!departmentOpt.get().getCompanyId().equals(employee.getCompanyId())) {
            throw new IllegalArgumentException("目标部门不属于员工当前所在的公司");
        }
        
        employee.updateDepartment(departmentId);
        employeeRepository.save(employee);
    }

    /**
     * 激活员工
     */
    public void activateEmployee(UUID employeeId)
    {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId.toString());
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("员工不存在");
        }

        Employee employee = employeeOpt.get();
        employee.activate();
        employeeRepository.save(employee);
    }

    /**
     * 停用员工
     */
    public void deactivateEmployee(UUID employeeId)
    {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId.toString());
        if (employeeOpt.isEmpty()) {
            throw new IllegalArgumentException("员工不存在");
        }

        Employee employee = employeeOpt.get();
        employee.deactivate();
        employeeRepository.save(employee);
    }
}