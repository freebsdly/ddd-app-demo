package com.example.demo.organization.application;

import com.example.demo.organization.infrastructure.entity.CompanyEntity;
import com.example.demo.organization.infrastructure.entity.DepartmentEntity;
import com.example.demo.organization.infrastructure.entity.EmployeeEntity;
import org.example.organization.domain.model.Company;
import org.example.organization.domain.model.Department;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntityMapper
{
    CompanyEntityDto toDto(Company company);

    Company toDomainEntity(CompanyEntity companyEntity);

    CompanyEntity toJpaEntity(Company company);

    @ObjectFactory
    default Company createCompany(CompanyEntity companyEntity)
    {
        return Company.create(companyEntity.getName(), companyEntity.isActive());
    }

    @AfterMapping
    default void mapCompanyFields(@MappingTarget CompanyEntity companyEntity, Company company)
    {
        companyEntity.setId(company.getId());
        companyEntity.setName(company.getName());
        companyEntity.setActive(company.isActive());
        companyEntity.setCreatedAt(company.getCreatedAt());
        companyEntity.setUpdatedAt(company.getUpdatedAt());
    }

    DepartmentEntity toEntity(DepartmentEntityDto departmentEntityDto);

    DepartmentEntityDto toDto(DepartmentEntity departmentEntity);


    DepartmentEntity toJpaEntity(Department department);

    Department toDomainEntity(DepartmentEntity departmentEntity);

    @ObjectFactory
    default Department createDepartment(DepartmentEntity departmentEntity)
    {
        return Department.create(departmentEntity.getName(), departmentEntity.getCode(),
                departmentEntity.getCompanyId());
    }

    @AfterMapping
    default void mapDepartmentFields(@MappingTarget DepartmentEntity departmentEntity, Department department)
    {
        departmentEntity.setId(department.getId());
        departmentEntity.setName(department.getName());
        departmentEntity.setCode(department.getCode());
        departmentEntity.setCompanyId(department.getCompanyId());
        departmentEntity.setCreatedAt(department.getCreatedAt());
        departmentEntity.setUpdatedAt(department.getUpdatedAt());
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DepartmentEntity partialUpdate(
            DepartmentEntityDto departmentEntityDto, @MappingTarget DepartmentEntity departmentEntity);

    EmployeeEntity toEntity(EmployeeEntityDto employeeEntityDto);

    EmployeeEntityDto toDto(EmployeeEntity employeeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EmployeeEntity partialUpdate(
            EmployeeEntityDto employeeEntityDto, @MappingTarget EmployeeEntity employeeEntity);
}