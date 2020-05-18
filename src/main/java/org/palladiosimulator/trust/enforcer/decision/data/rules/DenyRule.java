package org.palladiosimulator.trust.enforcer.decision.data.rules;

import java.util.Objects;

import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;

public class DenyRule extends Rule {
    private PrivacyLevel privacyLevel;

    public DenyRule(DataObject subject, Operation action, DataObject object, PrivacyLevel privacyLevel) {
        super(subject, action, object);
        if(privacyLevel == null)
        	throw new IllegalArgumentException("Privacylevel can't be null");
        this.privacyLevel = privacyLevel;
    }

    public PrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        DenyRule denyRule = (DenyRule) o;
        return privacyLevel.equals(denyRule.privacyLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), privacyLevel);
    }

    @Override
    public String toString() {
    	return "[" + this.getSubject() + " " + this.getOperation() + " " + this.getObject() + " " + getPrivacyLevel() + "]";
    }

}
