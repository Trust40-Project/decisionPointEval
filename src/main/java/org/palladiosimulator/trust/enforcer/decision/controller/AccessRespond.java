package org.palladiosimulator.trust.enforcer.decision.controller;

public class AccessRespond {
	private boolean allowed;
	public AccessRespond(boolean access) {
		this.allowed = access;
	}
	public boolean getAllowed() {
		return allowed;
	}

}
