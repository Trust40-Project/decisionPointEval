package org.palladiosimulator.trust.enforcer.decision.validation;

/**
 *
 */
public interface DesignTimeDecisionMaker {

    boolean checkRequest(String subject, String operation, String object) throws NullPointerException;

}
