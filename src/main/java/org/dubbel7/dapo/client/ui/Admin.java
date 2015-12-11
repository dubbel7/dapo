package org.dubbel7.dapo.client.ui;

import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame {

    public Admin() throws HeadlessException {
        initUI();
    }


    private void initUI() {

        setTitle("Admin");

        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(1000, 700));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PanelManager panelManager = new PanelManager(new LimitsGridDataSource());

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
                LimitsAdmin ex = new LimitsAdmin();
                ex.setVisible(true);
            }
        });

    }
}
