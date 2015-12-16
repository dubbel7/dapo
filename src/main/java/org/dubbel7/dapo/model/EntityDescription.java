package org.dubbel7.dapo.model;

public class EntityDescription {


    private final String name;
    private final String primaryKeyName;
    private final String[] columns;


    public EntityDescription(String name, String primaryKeyName, String[] columns) {
        this.name = name;
        this.primaryKeyName = primaryKeyName;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public String[] getColumns() {
        return columns;
    }
}
