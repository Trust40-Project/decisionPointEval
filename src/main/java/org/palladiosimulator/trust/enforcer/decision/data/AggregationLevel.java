package org.palladiosimulator.trust.enforcer.decision.data;

public enum AggregationLevel {

    // The order of appearing describes the order of the enumeration
    YEAR("year"), QUARTER("quarter"), MONTH("month"), WEEK("week"), DAY("day"), HOUR("hour"), MINUTE("minute");

    private final String name;

    private AggregationLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
