package com.example.demo.organization.interfaces.api;

import com.example.demo.organization.application.CompanyEntityDto;
import com.example.demo.organization.application.DepartmentEntityDto;
import com.example.demo.organization.application.EmployeeEntityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

public interface OrganizationApiDoc
{
    @Tag(name = "Company API")
    @Operation(summary = "Create a company")
    public CompanyEntityDto createCompany(CompanyEntityDto companyDto);

    @Tag(name = "Company API")
    @Operation(summary = "Update a company")
    public CompanyEntityDto updateCompany(UUID companyId, CompanyEntityDto companyDto);

    @Tag(name = "Company API")
    @Operation(summary = "Delete a company")
    public void deleteCompany(UUID companyId);

    @Tag(name = "Company API")
    @Operation(summary = "Get a company")
    public CompanyEntityDto getCompany(UUID companyId);

    @Tag(name = "Company API")
    @Operation(summary = "Get all companies")
    public Iterable<CompanyEntityDto> getAllCompanies();

    @Tag(name = "Department API")
    @Operation(summary = "Create a department")
    public DepartmentEntityDto createDepartment(DepartmentEntityDto departmentDto);

    @Tag(name = "Department API")
    @Operation(summary = "Update a department")
    public DepartmentEntityDto updateDepartment(UUID departmentId, DepartmentEntityDto departmentDto);

    @Tag(name = "Department API")
    @Operation(summary = "Delete a department")
    public void deleteDepartment(UUID departmentId);

    @Tag(name = "Department API")
    @Operation(summary = "Get a department")
    public DepartmentEntityDto getDepartment(UUID departmentId);

    @Tag(name = "Department API")
    @Operation(summary = "Get all departments")
    public Iterable<DepartmentEntityDto> getAllDepartments();

    @Tag(name = "Employee API")
    @Operation(summary = "Create an employee")
    public EmployeeEntityDto createEmployee(EmployeeEntityDto employeeDto);

    @Tag(name = "Employee API")
    @Operation(summary = "Update an employee")
    public EmployeeEntityDto updateEmployee(UUID employeeId, EmployeeEntityDto employeeDto);

    @Tag(name = "Employee API")
    @Operation(summary = "Delete an employee")
    public void deleteEmployee(UUID employeeId);

    @Tag(name = "Employee API")
    @Operation(summary = "Get an employee")
    public EmployeeEntityDto getEmployee(UUID employeeId);

    @Tag(name = "Employee API")
    @Operation(summary = "Get all employees")
    public Iterable<EmployeeEntityDto> getAllEmployees();

}
