package com.infinite.jsf.Provider.model;

import java.time.LocalDateTime;

import lombok.Data;
@Data


public class PasswordHistory {
	private int id;
    private Provider provider;
    private String passwordHash;
    private LocalDateTime changedAt;
    public PasswordHistory() {}

    
    public PasswordHistory(Provider provider, String passwordHash) {
        this.provider = provider;
        this.passwordHash = passwordHash;
    }

    
    protected void onCreate() {
        this.changedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}


