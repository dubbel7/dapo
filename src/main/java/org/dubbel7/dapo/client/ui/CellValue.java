package org.dubbel7.dapo.client.ui;

import java.util.concurrent.atomic.AtomicBoolean;

public class CellValue {

    public String currentValue;

    public volatile long currentUpdateId;
    public AtomicBoolean recentlyModified = new AtomicBoolean(false);

    public CellValue(String currentValue) {
        this.currentValue = currentValue;
    }


    @Override
    public String toString() {
        return currentValue;
    }
}
