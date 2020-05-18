package org.palladiosimulator.trust.enforcer.decision.controller.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.trust.enforcer.decision.data.DataObject;
import org.palladiosimulator.trust.enforcer.decision.data.Operation;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyTable;

public class TestPrivacyTable {
    @BeforeAll
    static void init() {
    }
    @Test
    void testExceptions() {
        String[][] tableWrongColumns = new String[1][0];
        String[][] tableWrondRows = new String[0][4];
        assertAll("wrong dimension",
                () -> assertThrows(IllegalArgumentException.class, () -> new PrivacyTable(tableWrongColumns)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PrivacyTable(tableWrondRows))
                );
        assertAll("null",
                () -> assertThrows(NullPointerException.class, () -> new PrivacyTable(null))
        );

    }

    @Test
    void testPrivacyLevelSearch(){
        PrivacyTable privacyTable = TestHelper.getPrivacyTable();
        assertAll("test Privacylevel",
                ()-> assertEquals(PrivacyLevel.PUBLIC, privacyTable.getPrivacyLevel(new DataObject("foreman"),new Operation("read"), new DataObject("worker")).get()),
                ()-> assertEquals(PrivacyLevel.SENSITIVE, privacyTable.getPrivacyLevel(new DataObject("foreman"),new Operation("read"), new DataObject("machine")).get()),
                ()-> assertEquals(PrivacyLevel.HIGHLY_SENSITIVE, privacyTable.getPrivacyLevel(new DataObject("worker"),new Operation("read"), new DataObject("machine")).get())
        );
    }
    @AfterAll
    static void clean() {

    }
}
