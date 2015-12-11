package org.dubbel7.dapo.client.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {

    private final JMenu menu;

    private final PanelManager panelManager;


    public MenuBar(PanelManager panelManager) {
        this.panelManager = panelManager;

        menu = new JMenu("Configuration");
        add(menu);

        addItem("Config");

    }

    private void addItem(String name) {
        JMenuItem m = new JMenuItem(name);
        menu.add(m);
        m.addActionListener(new MenuItemActionListener(name));
    }

    private class MenuItemActionListener implements ActionListener {
        private final String name;

        private MenuItemActionListener(String name) {
            this.name = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panelManager.addTab(name);
        }
    }
}
