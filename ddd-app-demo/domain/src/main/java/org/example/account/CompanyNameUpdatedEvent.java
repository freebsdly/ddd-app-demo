package org.example.account;

import java.util.UUID;

public class CompanyNameUpdatedEvent extends DomainEvent {
    private final UUID companyId;
    private final String oldName;
    private final String newName;
    
    public CompanyNameUpdatedEvent(UUID companyId, String oldName, String newName) {
        this.companyId = companyId;
        this.oldName = oldName;
        this.newName = newName;
    }
    
    public UUID getCompanyId() {
        return companyId;
    }
    
    public String getOldName() {
        return oldName;
    }
    
    public String getNewName() {
        return newName;
    }
}