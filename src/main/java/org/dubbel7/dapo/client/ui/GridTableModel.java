package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.model.Entity;
import org.dubbel7.dapo.model.EntityDescription;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridTableModel extends AbstractTableModel {

    private final EntityDescription ed;
    private final List<Map<String, CellValue>> data = new ArrayList<Map<String, CellValue>>();

    public DiffTableModel(EntityDescription ed) {
        this.ed = ed;
    }

    public void addRow(Map<String, String> row) {
        Map<String, CellValue> r = new HashMap<String, CellValue>();
        for(Map.Entry<String, String> e : row.entrySet()) {
            r.put(e.getKey(), new CellValue(e.getValue()));
        }
        data.add(r);
    }

    public boolean updateRow(Entity entity, long updateId) {
        for(Map<String, CellValue> d : data) {
            if(entity.getPrimaryKey().equals(d.get(entity.getPrimaryKeyName()).currentValue)) {
                for(Map.Entry<String, String> e : entity.getFields().entrySet()) {
                    String key = e.getKey();
                    String value = e.getValue();
                    CellValue cellValue = d.get(key);
                    if(!cellValue.currentValue.equals(value) ) {
                        cellValue.currentValue = value;
                        cellValue.currentUpdateId = updateId;
                        cellValue.recentlyModified.set(true);
                    }
                }
                fireTableDataChanged();
                return true;
            }
        }
        return false;
    }

    public void resetHighglight(String primaryKey, long uid) {
        for(Map<String, CellValue> d : data) {
            if(primaryKey.equals(d.get(ed.getPrimaryKeyName()).currentValue)) {
                for(Map.Entry<String, CellValue> e : d.entrySet()) {
                    CellValue p = e.getValue();
                    if(p.currentUpdateId == uid) {
                        p.recentlyModified.set(false);
                    }
                }
            }
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return ed.getColumns().length;
    }

    public String getColumnName(int col) {
        return ed.getColumns()[col];
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String columnName = getColumnName(columnIndex);
        Map<String, CellValue> map = data.get(rowIndex);
        if(map != null) {
            return map.get(columnName);
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //no cell editing
//        CellValue value = (CellValue) aValue;
//        String columnName = getColumnName(columnIndex);
//        data.get(rowIndex).get(columnName).setNewValue(value.currentValue);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CellValue.class;
    }

}
