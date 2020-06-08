package org.palladiosimulator.trust.enforcer.decision.data;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Stores the characteristic for each triple of Subject, Operation, Object
 *
 * It uses internally a 2d array to store the characteristics
 */
public class CharacteristicTable<CT extends Enum<CT>> {
    /**
     * Position of SUBJECT
     */
    private final static int SUBJECT = 0;
    /**
     * Position of operation
     */
    private final static int OPERATION = 1;
    /**
     * Position of object
     */
    private final static int OBJECT = 2;
    /**
     * Position
     */
    private final static int CHARACTERISTIC = 3;

    private final Class<CT> enumClass;
    private String[][] characteristicTable;
    
    /**
     * Constructor for the characteristic table
     * @param table 2-dimensional array as a table with 4 columns (subject,operation,object,characteristic)
     */
    public CharacteristicTable (Class<CT> enumClass, String [][] table){
        this.enumClass = enumClass;
        characteristicTable = Objects.requireNonNull(table);
        if(table.length == 0)
            throw new IllegalArgumentException("Table must be >0");
        if(table[0].length != 4){
            throw  new IllegalArgumentException("Table needs 4 columns");
        }
    }

    /**
     * Returns if a characteristic exists for a given triple
     * @param subject DataObject as the subject
     * @param operation Operation with the operation name, can be allquantified
     * @param object DataObject with the object of the request
     * @return Optional with the characteristic
     */
    public Optional<CT> getCharacteritic(DataObject subject, Operation operation, DataObject object){
        return Arrays.stream(characteristicTable).filter(e-> {
            Operation checkOperation = Operation.parseOperation(e[OPERATION]);
            boolean operationExpression = checkOperation.equalOperation(operation);
            boolean subjectExpression = e[SUBJECT].equals(subject.getType());
            boolean objectExpression =  e[OBJECT].equals(object.getType());
            return operationExpression && subjectExpression && objectExpression;
        }).map(e-> Enum.valueOf(enumClass, e[CHARACTERISTIC].toUpperCase())).findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacteristicTable<?> that = (CharacteristicTable<?>) o;
        return Arrays.deepEquals(characteristicTable, that.characteristicTable);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(characteristicTable);
    }
}
