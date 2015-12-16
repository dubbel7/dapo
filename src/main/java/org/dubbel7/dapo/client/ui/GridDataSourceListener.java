package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.model.Entity;

public interface GridDataSourceListener {

    void onAdd(Entity e);

    void onUpdate(Entity e);
}
