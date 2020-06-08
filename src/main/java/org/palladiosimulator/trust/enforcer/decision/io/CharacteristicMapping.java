package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacteristicMapping<CT extends Enum<CT>> extends CSVLoader {

    private final Class<CT> enumClass;

    public CharacteristicMapping(Class<CT> enumClass, String path) {
        super(path);
        this.enumClass = enumClass;
    }

    private Map<String, CT> createMap(String[][] datatypeMap) {
        if (datatypeMap == null)
            throw new IllegalArgumentException("Privacy Map can't be null");
        if (datatypeMap[0].length != 2)
            throw new IllegalArgumentException("Size of 2d dimension needs to be 2");
        return Arrays.stream(datatypeMap)
            .collect(Collectors.toMap(e -> e[0], e -> Enum.valueOf(enumClass, e[1].toUpperCase())));
    }

    public Map<String, CT> getTypeMapping() throws IOException {
        String[][] characteristicStrings = super.loadCSVFile(2);
        return createMap(characteristicStrings);
    }

}
