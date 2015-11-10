package org.dubbel7.dapo.client;

import java.util.List;

public interface Client {

    public void save(Entity e);

    public List<Entity> getAll(String type);

    public void registerListener(String type, Listener<Entity> l);

}
