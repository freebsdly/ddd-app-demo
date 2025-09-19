package org.example.organization.domain.service;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.organization.domain.model.Company;
import org.example.organization.domain.model.Department;
import org.example.organization.domain.model.Employee;
import org.example.organization.domain.repository.CompanyRepository;
import org.example.organization.domain.repository.DepartmentRepository;
import org.example.organization.domain.repository.EmployeeRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentSteps
{
    private DepartmentRepository departmentRepository;
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    private DepartmentDomainService departmentDomainService;
    private CompanyDomainService companyDomainService = new CompanyDomainService(companyRepository, null);
    private CompanyApplicationService companyApplicationService = new CompanyApplicationService(companyDomainService,
            event -> {
            });
    private Department department;
    private Company company;
    private List<Employee> employees;
    private Exception exception;
    private UUID departmentId;
    private UUID companyId;
    private UUID newCompanyId;
    private UUID parentId;
    private String companyName = "Test Company";

    @Given("我有有效的部门信息，名称为{string}，代码为{string}")
    public void i_have_valid_department_info(String name, String code)
    {
        // 准备测试数据
        companyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        // 初始化mock
        companyRepository = mock(CompanyRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        employeeRepository = mock(EmployeeRepository.class);

        company = companyApplicationService.createCompany(companyName, true);

        // 设置公司存在
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));

        departmentDomainService = new DepartmentDomainService(departmentRepository, companyRepository,
                employeeRepository);
    }

    @Given("id为{string}的公司存在")
    public void company_exists(String companyIdStr)
    {
        companyId = UUID.fromString(companyIdStr);
        company = mock(Company.class);
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));
    }

    @When("我创建一个新部门")
    public void i_create_a_new_department()
    {
        try {
            department = departmentDomainService.createDepartment("IT Department", "IT001", companyId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门应该被成功创建")
    public void department_should_be_created_successfully()
    {
        assertNotNull(department);
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentCreatedEvent事件")
    public void department_created_event_should_be_published()
    {
        assertFalse(department.getDomainEvents().isEmpty());
        assertEquals(1, department.getDomainEvents().size());
        assertTrue(department.getDomainEvents().get(0).getClass().getSimpleName().equals("DepartmentCreatedEvent"));
    }

    @Given("id为{string}的部门存在")
    public void department_exists(String departmentIdStr)
    {
        departmentId = UUID.fromString(departmentIdStr);

        // 初始化mock
        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);

        department = mock(Department.class);
        when(departmentRepository.findById(departmentId.toString())).thenReturn(Optional.of(department));

        departmentDomainService = new DepartmentDomainService(departmentRepository, companyRepository,
                employeeRepository);
    }

    @When("我将部门名称更新为{string}")
    public void i_update_department_name_to(String newName)
    {
        try {
            departmentDomainService.updateDepartmentName(departmentId, newName);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门名称应该被更新")
    public void department_name_should_be_updated()
    {
        verify(department).updateName(anyString());
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentNameUpdatedEvent事件")
    public void department_name_updated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @When("我将部门代码更新为{string}")
    public void i_update_department_code_to(String newCode)
    {
        try {
            departmentDomainService.updateDepartmentCode(departmentId, newCode);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门代码应该被更新")
    public void department_code_should_be_updated()
    {
        verify(department).updateCode(anyString());
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentCodeUpdatedEvent事件")
    public void department_code_updated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @Given("id为{string}的停用部门存在")
    public void inactive_department_exists(String departmentIdStr)
    {
        departmentId = UUID.fromString(departmentIdStr);

        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);

        department = mock(Department.class);
        when(departmentRepository.findById(departmentId.toString())).thenReturn(Optional.of(department));

        departmentDomainService = new DepartmentDomainService(departmentRepository, companyRepository,
                employeeRepository);
    }

    @When("我激活该部门")
    public void i_activate_the_department()
    {
        try {
            departmentDomainService.activateDepartment(departmentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门应该被激活")
    public void department_should_be_activated()
    {
        verify(department).activate();
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentActivatedEvent事件")
    public void department_activated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @Given("id为{string}的活动部门存在")
    public void active_department_exists(String departmentIdStr)
    {
        departmentId = UUID.fromString(departmentIdStr);

        departmentRepository = mock(DepartmentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);

        department = mock(Department.class);
        when(departmentRepository.findById(departmentId.toString())).thenReturn(Optional.of(department));

        departmentDomainService = new DepartmentDomainService(departmentRepository, companyRepository,
                employeeRepository);
    }

    @Given("该部门没有员工")
    public void the_department_has_no_employees()
    {
        when(employeeRepository.findByDepartmentId(anyString())).thenReturn(Collections.emptyList());
    }

    @When("我停用该部门")
    public void i_deactivate_the_department()
    {
        try {
            departmentDomainService.deactivateDepartment(departmentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门应该被停用")
    public void department_should_be_deactivated()
    {
        verify(department).deactivate();
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentDeactivatedEvent事件")
    public void department_deactivated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @Given("该部门有员工")
    public void the_department_has_employees()
    {
        employees = new ArrayList<>();
        employees.add(mock(Employee.class));
        when(employeeRepository.findByDepartmentId(anyString())).thenReturn(employees);
    }

    @When("我尝试停用该部门")
    public void i_try_to_deactivate_the_department()
    {
        try {
            departmentDomainService.deactivateDepartment(departmentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("应该抛出异常，消息为{string}")
    public void should_throw_exception_with_message(String expectedMessage)
    {
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Given("id为{string}的新公司存在")
    public void new_company_exists(String companyIdStr)
    {
        newCompanyId = UUID.fromString(companyIdStr);
        Company newCompany = mock(Company.class);
        when(companyRepository.findById(newCompanyId.toString())).thenReturn(Optional.of(newCompany));
    }

    @When("我更新部门所属公司")
    public void i_update_department_company()
    {
        try {
            departmentDomainService.updateDepartmentCompany(departmentId, newCompanyId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门所属公司应该被更新")
    public void department_company_should_be_updated()
    {
        verify(department).updateCompanyId(any(UUID.class));
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentCompanyUpdatedEvent事件")
    public void department_company_updated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @When("我尝试更新部门所属公司")
    public void i_try_to_update_department_company()
    {
        try {
            departmentDomainService.updateDepartmentCompany(departmentId, newCompanyId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("id为{string}的父部门存在")
    public void parent_department_exists(String parentIdStr)
    {
        parentId = UUID.fromString(parentIdStr);
        Department parentDepartment = mock(Department.class);
        when(departmentRepository.findById(parentId.toString())).thenReturn(Optional.of(parentDepartment));
    }

    @When("我更新部门父部门")
    public void i_update_department_parent()
    {
        try {
            departmentDomainService.updateDepartmentParent(departmentId, parentId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("部门父部门应该被更新")
    public void department_parent_should_be_updated()
    {
        verify(department).updateParentId(any(UUID.class));
        verify(departmentRepository).save(any(Department.class));
    }

    @Then("应该发布DepartmentParentUpdatedEvent事件")
    public void department_parent_updated_event_should_be_published()
    {
        verify(department).getDomainEvents();
    }

    @When("我尝试将该部门设置为其自身的父部门")
    public void i_try_to_set_department_as_its_own_parent()
    {
        try {
            departmentDomainService.updateDepartmentParent(departmentId, departmentId);
        } catch (Exception e) {
            exception = e;
        }
    }
}