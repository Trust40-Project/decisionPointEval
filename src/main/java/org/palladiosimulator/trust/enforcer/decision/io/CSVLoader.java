package org.palladiosimulator.trust.enforcer.decision.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public abstract class CSVLoader {
    private static final char SEPERATOR_CHAR = ';';
    private final String csvPath;

    public CSVLoader(String path) {
        csvPath = path;
    }

    /**
     * Method to load
     *
     * @param coloumns
     * @return
     */
    protected String[][] loadCSVFile(int coloumns) throws IOException {
        if (coloumns < 1)
            throw new IllegalArgumentException("The number of coloumns needs to be bigger than 1");
        return Files.lines(Paths.get(csvPath))
            .filter(e -> !e.startsWith("%"))
            .map(e -> e.split(SEPERATOR_CHAR + ""))
            .toArray(String[][]::new);
    }

}
