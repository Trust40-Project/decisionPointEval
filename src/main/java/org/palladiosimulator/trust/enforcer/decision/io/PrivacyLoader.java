package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;

import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;
import org.palladiosimulator.trust.enforcer.decision.data.PrivacyTable;

/**
 * Class for loading the privacy level
 */
public class PrivacyLoader extends CSVLoader {

    public PrivacyLoader(String path) {
        super(path);
    }

    /**
     * Loads the privacy level and returns a PrivacyTable with the triple (subject, operation, object) and the according privacy level
     *
     * @return {@link PrivacyTable} with the {@link PrivacyLevel}
     * @throws IOException If there are errors during the file reading
     */
    public PrivacyTable getPrivacyTable() throws IOException {
        String[][] privacyStrings = loadCSVFile(4);
        return new PrivacyTable(privacyStrings);
    }
}
