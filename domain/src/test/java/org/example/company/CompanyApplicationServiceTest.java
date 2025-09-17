package org.example.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CompanyApplicationServiceTest
{

    private CompanyApplicationService companyApplicationService;
    private CompanyDomainService companyDomainService;
    private CompanyChecker companyChecker;
    private DepartmentChecker departmentChecker;
    private DomainEventPublisher domainEventPublisher;

    @BeforeEach
    void setUp()
    {
        companyDomainService = mock(CompanyDomainService.class);
        companyChecker = mock(CompanyChecker.class);
        departmentChecker = mock(DepartmentChecker.class);
        domainEventPublisher = mock(DomainEventPublisher.class);

        companyApplicationService = new CompanyApplicationService(
                companyDomainService,
                companyChecker,
                departmentChecker,
                domainEventPublisher
        );
    }

    @Test
    void shouldCreateCompany()
    {
        // given
        String name = "Test Company";
        boolean active = true;
        Company company = Company.create(name, active);
        when(companyDomainService.createCompany(name, active, companyChecker))
                .thenReturn(company);

        // when
        Company result = companyApplicationService.createCompany(name, active);

        // then
        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(companyDomainService).createCompany(name, active, companyChecker);
        // 验证事件被发布
        verify(domainEventPublisher).publish(any(CompanyCreatedEvent.class));
    }

    @Test
    void shouldUpdateCompanyInfo()
    {
        // given
        UUID companyId = UUID.randomUUID();
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        String contactPhone = "1234567890";
        String email = "test@example.com";
        String businessScope = "IT Services";

        // 使用带ID参数的create方法构造Company实例
        Company company = Company.create(companyId, "Test Company", null, null, null, null, true,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        when(companyDomainService.findById(companyId)).thenReturn(Optional.of(company));

        // when
        companyApplicationService.updateCompanyInfo(companyId, address, contactPhone, email, businessScope);

        // then
        verify(companyDomainService).updateCompanyInfo(companyId, address, contactPhone, email, businessScope);
        // 验证事件被发布
        verify(domainEventPublisher).publish(any(CompanyInfoUpdatedEvent.class));
    }

    @Test
    void shouldUpdateCompanyName()
    {
        // given
        UUID companyId = UUID.randomUUID();
        String newName = "Updated Company Name";

        // 使用带ID参数的create方法构造Company实例
        Company company = Company.create(companyId, "Test Company", null, null, null, null, true,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        when(companyDomainService.findById(companyId)).thenReturn(Optional.of(company));

        // when
        companyApplicationService.updateCompanyName(companyId, newName);

        // then
        verify(companyDomainService).updateCompanyName(companyId, newName, companyChecker);
        // 验证事件被发布
        verify(domainEventPublisher).publish(any(CompanyNameUpdatedEvent.class));
    }

    @Test
    void shouldActivateCompany()
    {
        // given
        UUID companyId = UUID.randomUUID();
        // 使用带ID参数的create方法构造Company实例，初始状态为非激活
        Company company = Company.create(companyId, "Test Company", null, null, null, null, false,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        when(companyDomainService.findById(companyId)).thenReturn(Optional.of(company));

        // when
        companyApplicationService.activateCompany(companyId);

        // then
        verify(companyDomainService).activateCompany(companyId);
        // 验证事件被发布
        verify(domainEventPublisher).publish(any(CompanyActivatedEvent.class));
    }

    @Test
    void shouldDeactivateCompany()
    {
        // given
        UUID companyId = UUID.randomUUID();
        // 使用带ID参数的create方法构造Company实例，初始状态为激活
        Company company = Company.create(companyId, "Test Company", null, null, null, null, true,
                java.time.LocalDateTime.now(), java.time.LocalDateTime.now());

        when(companyDomainService.findById(companyId)).thenReturn(Optional.of(company));
        when(departmentChecker.hasActiveDepartments(companyId)).thenReturn(false);

        // when
        companyApplicationService.deactivateCompany(companyId);

        // then
        verify(companyDomainService).deactivateCompany(companyId, departmentChecker);
        // 验证事件被发布
        verify(domainEventPublisher).publish(any(CompanyDeactivatedEvent.class));
    }
}