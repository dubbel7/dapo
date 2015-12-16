package org.dubbel7.dapo.model;

import java.io.Serializable;
import java.util.Map;

public class Entity implements Serializable {

    private final String type;
    private final String key;
    private final Map<String, String> fields;

    public Entity(String type, String key, Map<String, String> fields) {
        this.type = type;
        this.key = key;
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", fields=" + fields +
                '}';
    }
}
