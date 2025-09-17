package org.example.account;

import java.util.UUID;

public class CompanyActivatedEvent extends DomainEvent {
    private final UUID companyId;
    private final String companyName;
    
    public CompanyActivatedEvent(UUID companyId, String companyName) {
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