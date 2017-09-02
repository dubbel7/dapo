package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.client.HzClient;
import org.dubbel7.dapo.client.Listener;
import org.dubbel7.dapo.model.Entity;
import org.dubbel7.dapo.model.EntityDescription;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Admin extends JFrame {

    private final HzClient hzClient;

    public Admin() throws HeadlessException {
        hzClient = new HzClient();

        initUI();
    }


    private void initUI() {

        setTitle("Admin");

        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(1000, 700));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PanelManager panelManager = new PanelManager(new GridDataSource() {
            @Override
            public EntityDescription getDescription(String entityName) {
                return new EntityDescription(entityName, "Config", new String[]{"Key", "Value"});
            }

            @Override
            public List<Entity> getAll(String entityName) {
                System.out.println("get "  + entityName);
                return hzClient.getAll(entityName);
            }

            @Override
            public void subscribe(String entityName, GridDataSourceListener listener) {
                hzClient.registerListener(entityName, new Listener<Entity>() {
                    @Override
                    public void onAdd(Entity entity) {
                        listener.onAdd(entity);
                    }

                    @Override
                    public void onUpdate(Entity entity) {
                        listener.onUpdate(entity);
                    }
                });
            }
        });

        MenuBar mb = new MenuBar(panelManager);
        setJMenuBar(mb);

//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.addTab("Add User", new AddUserPanel(adminClient));

        add(panelManager);
//        panelManager.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        pack();
    }

    public static void main(String[] a) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Admin ex = new Admin();
                ex.setVisible(true);
            }
        });

    }
}
