package com.example.demo.organization.interfaces.api;

import com.example.demo.organization.application.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/organization")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class OrganizationApi implements OrganizationApiDoc
{
    private final CompanyApplicationService companyApplicationService;
    private final DepartmentApplicationService departmentApplicationService;
    private final EmployeeApplicationService employeeApplicationService;

    @PostMapping("/companies")
    @Override
    public CompanyEntityDto createCompany(CompanyEntityDto companyDto)
    {
        return null;
    }

    @PostMapping("/companies/{companyId}")
    @Override
    public CompanyEntityDto updateCompany(@PathVariable UUID companyId, CompanyEntityDto companyDto)
    {
        return null;
    }

    @DeleteMapping("/companies/{companyId}")
    @Override
    public void deleteCompany(@PathVariable UUID companyId)
    {

    }

    @GetMapping("/companies/{companyId}")
    @Override
    public CompanyEntityDto getCompany(@PathVariable UUID companyId)
    {
        return null;
    }

    @GetMapping("/companies")
    @Override
    public Iterable<CompanyEntityDto> getAllCompanies()
    {
        return null;
    }

    @PostMapping("/departments")
    @Override
    public DepartmentEntityDto createDepartment(DepartmentEntityDto departmentDto)
    {
        return null;
    }

    @PostMapping("/departments/{departmentId}")
    @Override
    public DepartmentEntityDto updateDepartment(@PathVariable UUID departmentId, DepartmentEntityDto departmentDto)
    {
        return null;
    }

    @DeleteMapping("/departments/{departmentId}")
    @Override
    public void deleteDepartment(@PathVariable UUID departmentId)
    {

    }

    @GetMapping("/departments/{departmentId}")
    @Override
    public DepartmentEntityDto getDepartment(@PathVariable UUID departmentId)
    {
        return null;
    }

    @GetMapping("/departments")
    @Override
    public Iterable<DepartmentEntityDto> getAllDepartments()
    {
        return null;
    }

    @PostMapping("/employees")
    @Override
    public EmployeeEntityDto createEmployee(EmployeeEntityDto employeeDto)
    {
        return null;
    }

    @PostMapping("/employees/{employeeId}")
    @Override
    public EmployeeEntityDto updateEmployee(@PathVariable UUID employeeId, EmployeeEntityDto employeeDto)
    {
        return null;
    }

    @DeleteMapping("/employees/{employeeId}")
    @Override
    public void deleteEmployee(@PathVariable UUID employeeId)
    {

    }

    @GetMapping("/employees/{employeeId}")
    @Override
    public EmployeeEntityDto getEmployee(@PathVariable UUID employeeId)
    {
        return null;
    }

    @GetMapping("/employees")
    @Override
    public Iterable<EmployeeEntityDto> getAllEmployees()
    {
        return null;
    }
}
