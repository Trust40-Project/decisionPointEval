package org.palladiosimulator.trust.enforcer.decision.data.rules;

import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;

/**
 * DTO for allow rules
 */
public class AllowRule extends Rule {


    public AllowRule(DataObject subject, Operation operation, DataObject object) {
        super(subject, operation, object);
    }
}
