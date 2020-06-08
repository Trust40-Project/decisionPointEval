package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleMapping extends CSVLoader {
    public RoleMapping(String path) {
        super(path);
    }

    private Map<String, String> createMap(String[][] roleMap) {
        if (roleMap == null)
            throw new IllegalArgumentException("Map can't be null");
        if (roleMap[0].length != 2)
            throw new IllegalArgumentException("Size of 2d dimension needs to be 2");
        return Arrays.stream(roleMap)
            .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    public Map<String, String> getTypeMapping() throws IOException {
        String[][] roleStrings = super.loadCSVFile(2);
        return createMap(roleStrings);
    }
}
