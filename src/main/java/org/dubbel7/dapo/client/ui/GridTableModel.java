package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.model.Entity;
import org.dubbel7.dapo.model.EntityDescription;

import javax.swing.table.AbstractTableModel;
import java.util.Map;

public class GridTableModel extends AbstractTableModel {

    public GridTableModel(EntityDescription ed) {
        //TODO
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public void addRow(Map<String, String> fields) {
        //TODO
    }

    public void updateRow(Entity e, long uid) {
        //TODO
    }

    public void resetHighglight(String key, long uid) {
        //TODO
    }
}
