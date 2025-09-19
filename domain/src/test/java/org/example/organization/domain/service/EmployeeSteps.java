package org.example.organization.domain.service;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.organization.domain.model.Employee;
import org.example.organization.domain.model.Department;
import org.example.organization.domain.model.Company;
import org.example.organization.domain.repository.EmployeeRepository;
import org.example.organization.domain.repository.DepartmentRepository;
import org.example.organization.domain.repository.CompanyRepository;

import java.util.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeSteps {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private CompanyRepository companyRepository;
    private EmployeeDomainService employeeDomainService;
    private Employee employee;
    private Department department;
    private Company company;
    private UUID employeeId;
    private UUID departmentId;
    private UUID companyId;
    private UUID newDepartmentId;
    private Exception exception;

    @Given("我有有效的员工信息，工号为{string}，姓名为{string}")
    public void i_have_valid_employee_info(String badge, String name) {
        // 初始化mock
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        
        employeeDomainService = new EmployeeDomainService(employeeRepository, departmentRepository, companyRepository);
    }

    @Given("id为{string}的部门存在")
    public void department_exists(String departmentIdStr) {
        departmentId = UUID.fromString(departmentIdStr);
        department = mock(Department.class);
        when(departmentRepository.findById(departmentId.toString())).thenReturn(Optional.of(department));
    }

    @Given("id为{string}的公司存在")
    public void company_exists(String companyIdStr) {
        companyId = UUID.fromString(companyIdStr);
        company = mock(Company.class);
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));
    }

    @Given("该部门属于该公司")
    public void department_belongs_to_company() {
        when(department.getCompanyId()).thenReturn(companyId);
    }

    @When("我创建一个新员工")
    public void i_create_a_new_employee() {
        try {
            employee = employeeDomainService.createEmployee("EMP001", "John Doe", departmentId, companyId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工应该被成功创建")
    public void employee_should_be_created_successfully() {
        assertNotNull(employee);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeCreatedEvent事件")
    public void employee_created_event_should_be_published() {
        assertFalse(employee.getDomainEvents().isEmpty());
        assertEquals(1, employee.getDomainEvents().size());
        assertTrue(employee.getDomainEvents().get(0).getClass().getSimpleName().equals("EmployeeCreatedEvent"));
    }

    @Given("id为{string}的员工存在")
    public void employee_exists(String employeeIdStr) {
        employeeId = UUID.fromString(employeeIdStr);
        
        // 初始化mock
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        
        employee = mock(Employee.class);
        when(employeeRepository.findById(employeeId.toString())).thenReturn(Optional.of(employee));
        
        employeeDomainService = new EmployeeDomainService(employeeRepository, departmentRepository, companyRepository);
    }

    @When("我将员工工号更新为{string}，姓名更新为{string}")
    public void i_update_employee_badge_and_name(String newBadge, String newName) {
        try {
            employeeDomainService.updateEmployeeBadgeAndName(employeeId, newBadge, newName);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工工号和姓名应该被更新")
    public void employee_badge_and_name_should_be_updated() {
        verify(employee).updateBadgeAndName(anyString(), anyString());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeUpdatedEvent事件")
    public void employee_updated_event_should_be_published() {
        verify(employee).getDomainEvents();
    }

    @When("我更新员工详细信息")
    public void i_update_employee_info() {
        try {
            org.example.organization.domain.model.EmployeeInfo employeeInfo = 
                new org.example.organization.domain.model.EmployeeInfo();
            employeeDomainService.updateEmployeeInfo(employeeId, employeeInfo);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工详细信息应该被更新")
    public void employee_info_should_be_updated() {
        verify(employee).updateEmployeeInfo(any(org.example.organization.domain.model.EmployeeInfo.class));
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeInfoUpdatedEvent事件")
    public void employee_info_updated_event_should_be_published() {
        verify(employee).getDomainEvents();
    }

    @Given("id为{string}的新部门存在于同一公司")
    public void new_department_exists_in_same_company(String departmentIdStr) {
        newDepartmentId = UUID.fromString(departmentIdStr);
        Department newDepartment = mock(Department.class);
        when(departmentRepository.findById(newDepartmentId.toString())).thenReturn(Optional.of(newDepartment));
        when(newDepartment.getCompanyId()).thenReturn(companyId);
    }

    @When("我更新员工部门")
    public void i_update_employee_department() {
        try {
            employeeDomainService.updateEmployeeDepartment(employeeId, newDepartmentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工部门应该被更新")
    public void employee_department_should_be_updated() {
        verify(employee).updateDepartment(any(UUID.class));
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeDepartmentUpdatedEvent事件")
    public void employee_department_updated_event_should_be_published() {
        verify(employee).getDomainEvents();
    }

    @Given("id为{string}的新部门存在于不同公司")
    public void new_department_exists_in_different_company(String departmentIdStr) {
        newDepartmentId = UUID.fromString(departmentIdStr);
        Department newDepartment = mock(Department.class);
        when(departmentRepository.findById(newDepartmentId.toString())).thenReturn(Optional.of(newDepartment));
        when(newDepartment.getCompanyId()).thenReturn(UUID.randomUUID()); // 不同的公司ID
    }

    @When("我尝试更新员工部门")
    public void i_try_to_update_employee_department() {
        try {
            employeeDomainService.updateEmployeeDepartment(employeeId, newDepartmentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("id为{string}的停用员工存在")
    public void inactive_employee_exists(String employeeIdStr) {
        employeeId = UUID.fromString(employeeIdStr);
        
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        
        employee = mock(Employee.class);
        when(employeeRepository.findById(employeeId.toString())).thenReturn(Optional.of(employee));
        
        employeeDomainService = new EmployeeDomainService(employeeRepository, departmentRepository, companyRepository);
    }

    @When("我激活该员工")
    public void i_activate_the_employee() {
        try {
            employeeDomainService.activateEmployee(employeeId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工应该被激活")
    public void employee_should_be_activated() {
        verify(employee).activate();
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeActivatedEvent事件")
    public void employee_activated_event_should_be_published() {
        verify(employee).getDomainEvents();
    }

    @Given("id为{string}的活动员工存在")
    public void active_employee_exists(String employeeIdStr) {
        employeeId = UUID.fromString(employeeIdStr);
        
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        
        employee = mock(Employee.class);
        when(employeeRepository.findById(employeeId.toString())).thenReturn(Optional.of(employee));
        
        employeeDomainService = new EmployeeDomainService(employeeRepository, departmentRepository, companyRepository);
    }

    @When("我停用该员工")
    public void i_deactivate_the_employee() {
        try {
            employeeDomainService.deactivateEmployee(employeeId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("员工应该被停用")
    public void employee_should_be_deactivated() {
        verify(employee).deactivate();
        verify(employeeRepository).save(any(Employee.class));
    }

    @Then("应该发布EmployeeDeactivatedEvent事件")
    public void employee_deactivated_event_should_be_published() {
        verify(employee).getDomainEvents();
    }
}