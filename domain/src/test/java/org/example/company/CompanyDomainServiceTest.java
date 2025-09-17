package org.example.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyDomainServiceTest {

    private CompanyDomainService companyDomainService;
    private CompanyRepository companyRepository;
    private CompanyChecker companyChecker;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyDomainService = new CompanyDomainService(companyRepository);
        companyChecker = mock(CompanyChecker.class);
    }

    @Test
    void shouldCreateCompany() {
        // given
        String name = "Test Company";
        boolean active = true;
        when(companyChecker.isNameUnique(name)).thenReturn(false);

        // when
        Company company = companyDomainService.createCompany(name, active, companyChecker);

        // then
        assertNotNull(company);
        assertEquals(name, company.getName());
        verify(companyRepository).save(company);
    }

    @Test
    void shouldThrowExceptionWhenCreatingCompanyWithDuplicateName() {
        // given
        String name = "Test Company";
        boolean active = true;
        when(companyChecker.isNameUnique(name)).thenReturn(true);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            companyDomainService.createCompany(name, active, companyChecker));
    }

    @Test
    void shouldUpdateCompanyInfo() {
        // given
        UUID companyId = UUID.randomUUID();
        Company company = Company.create("Test Company", true);
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        String contactPhone = "1234567890";
        String email = "test@example.com";
        String businessScope = "IT Services";

        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));

        // when
        companyDomainService.updateCompanyInfo(companyId, address, contactPhone, email, businessScope);

        // then
        verify(companyRepository).findById(companyId.toString());
        verify(companyRepository).save(company);
        assertEquals(address, company.getAddress());
        assertEquals(contactPhone, company.getContactPhone());
        assertEquals(email, company.getEmail());
        assertEquals(businessScope, company.getBusinessScope());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingInfoOfNonExistentCompany() {
        // given
        UUID companyId = UUID.randomUUID();
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            companyDomainService.updateCompanyInfo(companyId, address, "123", "test@example.com", "IT"));
    }

    @Test
    void shouldUpdateCompanyName() {
        // given
        UUID companyId = UUID.randomUUID();
        Company company = Company.create("Test Company", true);
        String newName = "New Company Name";
        
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));
        when(companyChecker.isNameUnique(newName)).thenReturn(false);

        // when
        companyDomainService.updateCompanyName(companyId, newName, companyChecker);

        // then
        verify(companyRepository).findById(companyId.toString());
        verify(companyRepository).save(company);
        assertEquals(newName, company.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNameOfNonExistentCompany() {
        // given
        UUID companyId = UUID.randomUUID();
        String newName = "New Company Name";
        
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            companyDomainService.updateCompanyName(companyId, newName, companyChecker));
    }

    @Test
    void shouldActivateCompany() {
        // given
        UUID companyId = UUID.randomUUID();
        Company company = Company.create("Test Company", false);
        
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));

        // when
        companyDomainService.activateCompany(companyId);

        // then
        verify(companyRepository).findById(companyId.toString());
        verify(companyRepository).save(company);
        assertTrue(company.isActive());
    }

    @Test
    void shouldDeactivateCompany() {
        // given
        UUID companyId = UUID.randomUUID();
        Company company = Company.create("Test Company", true);
        DepartmentChecker departmentChecker = mock(DepartmentChecker.class);
        
        when(departmentChecker.hasActiveDepartments(companyId)).thenReturn(false);
        when(companyRepository.findById(companyId.toString())).thenReturn(Optional.of(company));

        // when
        companyDomainService.deactivateCompany(companyId, departmentChecker);

        // then
        verify(companyRepository).findById(companyId.toString());
        verify(companyRepository).save(company);
        assertFalse(company.isActive());
    }
}