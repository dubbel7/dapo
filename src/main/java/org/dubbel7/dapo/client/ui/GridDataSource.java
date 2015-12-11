package org.dubbel7.dapo.client.ui;

import java.util.List;

public interface GridDataSource {

    public EntityDescription getDescription(String entityName);

    public List<Entity> getAll(String entityName);

    public void subscribe(String entityName, GridDataSourceListener listener);

}
