package org.palladiosimulator.trust.enforcer.decision.controller;

public class AccessResponse {
    private final boolean allowed;

    public AccessResponse(boolean access) {
        this.allowed = access;
    }

    public boolean getAllowed() {
        return allowed;
    }

}
