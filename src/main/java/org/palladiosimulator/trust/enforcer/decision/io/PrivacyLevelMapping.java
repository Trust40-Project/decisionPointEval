package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.palladiosimulator.trust.enforcer.decision.data.PrivacyLevel;

public class PrivacyLevelMapping extends CSVLoader {

	public PrivacyLevelMapping(String path) {
		super(path);
	}
    private Map<String, PrivacyLevel> createMap(String[][] datatypeMap) {
        if(datatypeMap == null)
            throw new IllegalArgumentException("Privacy Map can't be null");
        if (datatypeMap[0].length != 2)
            throw new IllegalArgumentException("Size of 2d dimension needs to be 2");
        return Arrays.stream(datatypeMap).collect(Collectors.toMap(e->e[0],e->PrivacyLevel.valueOf(e[1].toUpperCase())));
    }

    public Map<String, PrivacyLevel> getTypeMapping() throws IOException {
        String[][] privacyStrings = super.loadCSVFile(2);
        return createMap(privacyStrings);
    }

}
