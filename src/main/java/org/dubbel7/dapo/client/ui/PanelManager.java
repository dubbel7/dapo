package org.dubbel7.dapo.client.ui;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PanelManager extends JTabbedPane {

    private final Map<String, GridPanel> currentPanels = new ConcurrentHashMap<String, GridPanel>();
    private final GridDataSource gridDataSource;

    public PanelManager(GridDataSource gridDataSource) {
        this.gridDataSource = gridDataSource;
        setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    public void addTab(String name) {

        if(currentPanels.containsKey(name)) {
            int i = indexOfTab(name);
            if(i >= 0) {
                setSelectedIndex(i);
            }
        } else {

            GridPanel panel = new GridPanel(name, gridDataSource);
            currentPanels.put(panel.getName(), panel);
            addTab(panel.getName(), panel);
            setSelectedIndex(getTabCount() - 1);

            int index = indexOfTab(name);
            setTabComponentAt(index, new ButtonTabComponent(this));
        }
    }

    public void removeTab(int i) {
        if (i != -1) {
            String title = getTitleAt(i);
            GridPanel gp = currentPanels.remove(title);
            //gp.onRemoved();
            remove(i);
        }

    }
}
