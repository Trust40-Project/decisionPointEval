package org.palladiosimulator.trust.enforcer.decision.controller.test;

import org.palladiosimulator.trust.enforcer.decision.data.PrivacyTable;

public class TestHelper {
    public static PrivacyTable getPrivacyTable(){
        String[][] table = {{"foreman","read(*)","worker","public"},{"foreman","read(*)","machine","sensitive"},{"worker","read(*)","machine","highly_sensitive"}};
        return new PrivacyTable(table);
    }
}
