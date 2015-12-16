package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.model.Entity;
import org.dubbel7.dapo.model.EntityDescription;

import java.util.List;

public interface GridDataSource {

    EntityDescription getDescription(String entityName);

    List<Entity> getAll(String entityName);

    void subscribe(String entityName, GridDataSourceListener listener);

}
