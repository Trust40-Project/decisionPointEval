package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;

import org.palladiosimulator.trust.enforcer.decision.data.CharacteristicTable;

/**
 * Class for loading the characteristic
 */
public class CharacteristicLoader<CT extends Enum<CT>> extends CSVLoader {

    private final Class<CT> enumClass;

    public CharacteristicLoader(Class<CT> enumClass, String path) {
        super(path);
        this.enumClass = enumClass;
    }

    /**
     * Loads the characteristic and returns a CharacteristicTable with the triple (subject,
     * operation, object) and the according characteristic
     *
     * @return {@link CharacteristicTable}
     * @throws IOException
     *             If there are errors during the file reading
     */
    public CharacteristicTable<CT> getCharacteristicTable() throws IOException {
        String[][] characteristicStrings = loadCSVFile(4);
        return new CharacteristicTable<>(enumClass, characteristicStrings);
    }
}
