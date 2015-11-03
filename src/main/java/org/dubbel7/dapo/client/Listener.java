package org.dubbel7.dapo.client;

public interface Listener<T> {

    void onAdd(T t);

    void onUpdate(T t);
}
