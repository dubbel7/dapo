package org.dubbel7.dapo.client.ui;

import java.util.Map;

public class Entity {

    private final String entityName;
    private final String primaryKeyName;
    private final Map<String, String> fields;

    public Entity(String entityName, String primaryKeyName, Map<String, String> fields) {
        this.entityName = entityName;
        this.primaryKeyName = primaryKeyName;
        this.fields = fields;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public String getPrimaryKey() {
        return fields.get(primaryKeyName);
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityName='" + entityName + '\'' +
                ", fields=" + fields +
                '}';
    }
}
