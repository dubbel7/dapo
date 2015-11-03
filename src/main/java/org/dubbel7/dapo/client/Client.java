package org.dubbel7.dapo.client;

import java.util.List;

public interface Client {

    public void save(Entity e);

    public List<Entity> getAll();

    public void registerListener(Listener<Entity> l);

}
