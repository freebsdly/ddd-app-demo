package org.example.account;

import java.util.UUID;

public class CompanyInfoUpdatedEvent extends DomainEvent {
    private final UUID companyId;
    private final String companyName;
    
    public CompanyInfoUpdatedEvent(UUID companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }
    
    public UUID getCompanyId() {
        return companyId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
}