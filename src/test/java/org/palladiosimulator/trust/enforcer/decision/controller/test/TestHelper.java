package org.palladiosimulator.trust.enforcer.decision.controller.test;

import org.palladiosimulator.trust.enforcer.decision.data.CharacteristicTable;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;

public class TestHelper {
    public static CharacteristicTable<PrivacyLevel> getPrivacyTable() {
        String[][] table = { { "foreman", "read(*)", "worker", "public" },
                { "foreman", "read(*)", "machine", "sensitive" },
                { "worker", "read(*)", "machine", "highly_sensitive" } };
        return new CharacteristicTable<>(PrivacyLevel.class, table);
    }
}
