package org.example.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyTest
{

    private Company company;
    private CompanyChecker companyChecker;

    @BeforeEach
    void setUp()
    {
        companyChecker = mock(CompanyChecker.class);
        company = Company.create("Test Company", true);
    }

    @Test
    void shouldCreateCompanyWithValidParameters()
    {
        // when
        Company newCompany = Company.create("New Company", true);

        // then
        assertNotNull(newCompany.getId());
        assertEquals("New Company", newCompany.getName());
        assertTrue(newCompany.isActive());
        assertNotNull(newCompany.getCreatedAt());
        assertNotNull(newCompany.getUpdatedAt());

        // 验证领域事件被正确触发
        var domainEvents = newCompany.getDomainEvents();
        assertFalse(domainEvents.isEmpty());
        assertInstanceOf(CompanyCreatedEvent.class, domainEvents.getFirst());

        CompanyCreatedEvent event = (CompanyCreatedEvent) domainEvents.getFirst();
        assertEquals(newCompany.getId(), event.getCompanyId());
        assertEquals("New Company", event.getCompanyName());
        assertTrue(event.isActive());
    }

    @Test
    void shouldThrowExceptionWhenCreatingCompanyWithNullName()
    {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                Company.create(null, true));
    }

    @Test
    void shouldThrowExceptionWhenCreatingCompanyWithEmptyName()
    {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                Company.create("", true));
    }

    @Test
    void shouldUpdateCompanyInfo()
    {
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        Company company = Company.create(UUID.randomUUID(), "Test Company", address, "1234567890", "test@example.com",
                "IT Services", true, LocalDateTime.now(), LocalDateTime.now());
        // given
        Address address1 = new Address("1123 Main St", "1Beijing", "1Beijing", "1100000", "1China");
        String contactPhone = "11234567890";
        String email = "1test@example.com";
        String businessScope = "1IT Services";

        // when
        company.updateInfo(address1, contactPhone, email, businessScope);

        // then
        assertEquals(address1, company.getAddress());
        assertEquals(contactPhone, company.getContactPhone());
        assertEquals(email, company.getEmail());
        assertEquals(businessScope, company.getBusinessScope());
        List<DomainEvent> domainEvents = company.getDomainEvents();
        assertFalse(domainEvents.isEmpty());
        assertInstanceOf(CompanyInfoUpdatedEvent.class, domainEvents.getFirst());
    }

    @Test
    void shouldUpdateCompanyName()
    {
        // given
        String newName = "Updated Company Name";
        when(companyChecker.isNameUnique(newName)).thenReturn(false);

        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");

        Company company = Company.create(UUID.randomUUID(), "Test Company", address, "1234567890", "test@example.com",
                "IT Services", true, LocalDateTime.now(), LocalDateTime.now());

        // when
        company.updateName(newName, companyChecker);

        // then
        assertEquals(newName, company.getName());
        List<DomainEvent> domainEvents = company.getDomainEvents();
        assertFalse(domainEvents.isEmpty());
        assertInstanceOf(CompanyNameUpdatedEvent.class, domainEvents.getFirst());

        CompanyNameUpdatedEvent event = (CompanyNameUpdatedEvent) domainEvents.getFirst();
        assertEquals(company.getId(), event.getCompanyId());
        assertEquals("Test Company", event.getOldName());
        assertEquals(newName, event.getNewName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNullName()
    {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                company.updateName(null, companyChecker));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithEmptyName()
    {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                company.updateName("", companyChecker));
    }

    @Test
    void shouldActivateCompany()
    {
        // given
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        Company company = Company.create(UUID.randomUUID(), "Test Company", address, "1234567890", "test@example.com",
                "IT Services", false, LocalDateTime.now(), LocalDateTime.now());

        assertFalse(company.isActive());

        // when
        company.activate();

        // then
        assertTrue(company.isActive());
        var domainEvents = company.getDomainEvents();
        assertFalse(domainEvents.isEmpty());
        assertInstanceOf(CompanyActivatedEvent.class, domainEvents.getFirst());
    }

    @Test
    void shouldDeactivateCompany()
    {
        // given
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        Company company = Company.create(UUID.randomUUID(), "Test Company", address, "1234567890", "test@example.com",
                "IT Services", true, LocalDateTime.now(), LocalDateTime.now());

        DepartmentChecker departmentChecker = mock(DepartmentChecker.class);
        when(departmentChecker.hasActiveDepartments(company.getId())).thenReturn(false);

        // when
        company.deactivate(departmentChecker);

        // then
        assertFalse(company.isActive());
        var domainEvents = company.getDomainEvents();
        assertFalse(domainEvents.isEmpty());
        assertInstanceOf(CompanyDeactivatedEvent.class, domainEvents.getFirst());
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingCompanyWithActiveDepartments()
    {
        // given
        DepartmentChecker departmentChecker = mock(DepartmentChecker.class);
        when(departmentChecker.hasActiveDepartments(company.getId())).thenReturn(true);

        // when & then
        assertThrows(IllegalStateException.class, () ->
                company.deactivate(departmentChecker));
    }

    @Test
    void shouldReturnDomainEventsAndClearList()
    {
        // given
        // 公司创建时会自动添加一个CompanyCreatedEvent
        Company newCompany = Company.create("Test Company", true);
        // 验证创建事件存在
        var domainEvents = newCompany.getDomainEvents();
        assertEquals(1, domainEvents.size());
        assertInstanceOf(CompanyCreatedEvent.class, domainEvents.getFirst());
        // List should be cleared after getDomainEvents()
        assertTrue(newCompany.getDomainEvents().isEmpty());

        // 再次更新信息以添加CompanyInfoUpdatedEvent
        newCompany.updateInfo(
                new Address("123 Main St", "Beijing", "Beijing", "100000", "China"),
                "1234567890",
                "test@example.com",
                "IT Services"
                             );

        // when
        domainEvents = newCompany.getDomainEvents();

        // then
        assertEquals(1, domainEvents.size());
        assertInstanceOf(CompanyInfoUpdatedEvent.class, domainEvents.getFirst());
        // List should be cleared
        assertTrue(newCompany.getDomainEvents().isEmpty());
    }

    @Test
    void shouldTestCompanyEqualityBasedOnId()
    {
        // given
        Company company1 = Company.create("Company 1", true);

        // 使用反射来访问company1的私有id字段
        UUID id = company1.getId();

        // 创建一个新的Company实例，并通过反射设置相同的ID
        Company company2 = Company.create("Company 2", false);

        // 使用反射修改company2的ID以匹配company1的ID
        try {
            java.lang.reflect.Field idField = Company.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(company2, id);
        } catch (Exception e) {
            fail("Failed to set ID via reflection: " + e.getMessage());
        }

        // when & then
        assertEquals(company1, company2);
        assertEquals(company1.hashCode(), company2.hashCode());
    }

    @Test
    void shouldNotBeEqualWithDifferentIds()
    {
        // given
        Company company1 = Company.create("Company 1", true);
        Company company2 = Company.create("Company 1", true); // Same name, different ID

        // when & then
        assertNotEquals(company1, company2);
    }

    @Test
    void shouldLoadCompanyFromRepositoryWithoutEvents()
    {
        // given
        UUID id = UUID.randomUUID();
        String name = "Test Company";
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");
        String contactPhone = "1234567890";
        String email = "test@example.com";
        String businessScope = "IT Services";
        boolean active = true;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now().minusHours(1);

        // when
        Company loadedCompany = Company.create(id, name, address, contactPhone,
                email, businessScope, active, createdAt, updatedAt);

        // then
        assertEquals(id, loadedCompany.getId());
        assertEquals(name, loadedCompany.getName());
        assertEquals(address, loadedCompany.getAddress());
        assertEquals(contactPhone, loadedCompany.getContactPhone());
        assertEquals(email, loadedCompany.getEmail());
        assertEquals(businessScope, loadedCompany.getBusinessScope());
        assertEquals(active, loadedCompany.isActive());
        assertEquals(createdAt, loadedCompany.getCreatedAt());
        assertEquals(updatedAt, loadedCompany.getUpdatedAt());
        // 验证加载时不触发事件
        assertTrue(loadedCompany.getDomainEvents().isEmpty());
    }
}