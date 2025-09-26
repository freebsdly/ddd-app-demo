package com.example.demo.organization.interfaces.api;

import com.example.demo.organization.application.CompanyEntityDto;
import com.example.demo.organization.application.DepartmentEntityDto;
import com.example.demo.organization.application.EmployeeEntityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

public interface OrganizationApiDoc
{
    @Tag(name = "Company API")
    @Operation(summary = "Create a company")
    public ApiBody<CompanyEntityDto> createCompany(CompanyEntityDto companyDto);

    @Tag(name = "Company API")
    @Operation(summary = "Update a company")
    public ApiBody<CompanyEntityDto> updateCompany(UUID companyId, CompanyEntityDto companyDto);

    @Tag(name = "Company API")
    @Operation(summary = "Delete a company")
    public void deleteCompany(UUID companyId);

    @Tag(name = "Company API")
    @Operation(summary = "Get a company")
    public ApiBody<CompanyEntityDto> getCompany(UUID companyId);

    @Tag(name = "Company API")
    @Operation(summary = "Get all companies")
    public ApiBody<List<CompanyEntityDto>> getAllCompanies();

    @Tag(name = "Department API")
    @Operation(summary = "Create a department")
    public ApiBody<DepartmentEntityDto> createDepartment(DepartmentEntityDto departmentDto);

    @Tag(name = "Department API")
    @Operation(summary = "Update a department")
    public ApiBody<DepartmentEntityDto> updateDepartment(UUID departmentId, DepartmentEntityDto departmentDto);

    @Tag(name = "Department API")
    @Operation(summary = "Delete a department")
    public void deleteDepartment(UUID departmentId);

    @Tag(name = "Department API")
    @Operation(summary = "Get a department")
    public ApiBody<DepartmentEntityDto> getDepartment(UUID departmentId);

    @Tag(name = "Department API")
    @Operation(summary = "Get all departments")
    public ApiBody<List<DepartmentEntityDto>> getAllDepartments();

    @Tag(name = "Employee API")
    @Operation(summary = "Create an employee")
    public ApiBody<EmployeeEntityDto> createEmployee(EmployeeEntityDto employeeDto);

    @Tag(name = "Employee API")
    @Operation(summary = "Update an employee")
    public ApiBody<EmployeeEntityDto> updateEmployee(UUID employeeId, EmployeeEntityDto employeeDto);

    @Tag(name = "Employee API")
    @Operation(summary = "Delete an employee")
    public void deleteEmployee(UUID employeeId);

    @Tag(name = "Employee API")
    @Operation(summary = "Get an employee")
    public ApiBody<EmployeeEntityDto> getEmployee(UUID employeeId);

    @Tag(name = "Employee API")
    @Operation(summary = "Get all employees")
    public ApiBody<List<EmployeeEntityDto>> getAllEmployees();

}
