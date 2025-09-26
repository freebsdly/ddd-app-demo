package com.example.demo.organization.interfaces.api;

import com.example.demo.organization.application.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ApiBody<CompanyEntityDto> createCompany(@RequestBody CompanyEntityDto companyDto)
    {
        CompanyEntityDto company = companyApplicationService.createCompany(companyDto.getName(), companyDto.isActive());
        return ApiBody.success(company);
    }

    @PostMapping("/companies/{companyId}")
    @Override
    public ApiBody<CompanyEntityDto> updateCompany(@PathVariable UUID companyId,
            @RequestBody CompanyEntityDto companyDto)
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
    public ApiBody<CompanyEntityDto> getCompany(@PathVariable UUID companyId)
    {
        return null;
    }

    @GetMapping("/companies")
    @Override
    public ApiBody<List<CompanyEntityDto>> getAllCompanies()
    {
        return null;
    }

    @PostMapping("/departments")
    @Override
    public ApiBody<DepartmentEntityDto> createDepartment(@RequestBody DepartmentEntityDto departmentDto)
    {
        return null;
    }

    @PostMapping("/departments/{departmentId}")
    @Override
    public ApiBody<DepartmentEntityDto> updateDepartment(@PathVariable UUID departmentId,
            @RequestBody DepartmentEntityDto departmentDto)
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
    public ApiBody<DepartmentEntityDto> getDepartment(@PathVariable UUID departmentId)
    {
        return null;
    }

    @GetMapping("/departments")
    @Override
    public ApiBody<List<DepartmentEntityDto>> getAllDepartments()
    {
        return null;
    }

    @PostMapping("/employees")
    @Override
    public ApiBody<EmployeeEntityDto> createEmployee(@RequestBody EmployeeEntityDto employeeDto)
    {
        return null;
    }

    @PostMapping("/employees/{employeeId}")
    @Override
    public ApiBody<EmployeeEntityDto> updateEmployee(@PathVariable UUID employeeId,
            @RequestBody EmployeeEntityDto employeeDto)
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
    public ApiBody<EmployeeEntityDto> getEmployee(@PathVariable UUID employeeId)
    {
        return null;
    }

    @GetMapping("/employees")
    @Override
    public ApiBody<List<EmployeeEntityDto>> getAllEmployees()
    {
        return null;
    }
}
