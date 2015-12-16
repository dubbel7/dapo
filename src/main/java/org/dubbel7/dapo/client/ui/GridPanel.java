package org.dubbel7.dapo.client.ui;

import org.dubbel7.dapo.model.Entity;
import org.dubbel7.dapo.model.EntityDescription;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class GridPanel  extends JPanel implements ActionListener {

    private final AtomicLong updateId = new AtomicLong(0);
    private final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private final GridTableModel model;
    private final JTable table;

    private final Map<String, JTextField> fieldsMap = new HashMap<>();

    public GridPanel(String name, GridDataSource gridDataSource) {
        super(new BorderLayout());
        setName(name);

        EntityDescription ed = gridDataSource.getDescription(name);

        JPanel textControlsPane = new JPanel();
        textControlsPane.setLayout(new GridBagLayout());

        JLabel[] labels = getLabels(ed.getColumns());
        JTextField[] textFields = getTextFields(ed.getColumns());
        addLabelTextRows(labels, textFields, textControlsPane);

        textControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Fields"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));


        JToolBar toolBar = new JToolBar("Action Buttons");
        toolBar.setFloatable(false);
        addButtons(toolBar);


        JPanel fieldsPane = new JPanel(new BorderLayout());
        fieldsPane.add(textControlsPane, BorderLayout.PAGE_START);
        fieldsPane.add(toolBar, BorderLayout.PAGE_END);

        JPanel gridPanel = new JPanel(new BorderLayout());

        model = new GridTableModel(ed);
        List<Entity> all = gridDataSource.getAll(name);
        for(Entity e : all) {
            model.addRow(e.getFields());
        }

        table = new JTable(model);
        table.setDefaultRenderer(CellValue.class, new MyRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePane = new JPanel(new BorderLayout());
        tablePane.add(scrollPane, BorderLayout.CENTER);
        tablePane.setBorder(new EmptyBorder(5,5,5,5));

        JToolBar gridToolBar = new JToolBar("Grid Buttons");
        gridToolBar.setFloatable(false);
        addGridButtons(gridToolBar);

        gridPanel.add(tablePane, BorderLayout.CENTER);
        gridPanel.add(gridToolBar, BorderLayout.SOUTH);

        add(fieldsPane, BorderLayout.EAST);
        add(gridPanel, BorderLayout.CENTER);

        subscribeUpdates(name, gridDataSource);
    }

    private void subscribeUpdates(String name, GridDataSource gridDataSource) {
        gridDataSource.subscribe(name, new GridDataSourceListener() {
            @Override
            public void onAdd(Entity e) {
                System.out.println("added: " + e);
            }

            @Override
            public void onUpdate(Entity e) {
                System.out.println("updated: " + e);
                long uid = updateId.incrementAndGet();
                int selectedRow = table.getSelectedRow();
                model.updateRow(e, uid);
                if(selectedRow >= 0) {
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                }
                ex.schedule(new ResetTask(e.getKey(), uid), 1, TimeUnit.SECONDS);
            }
        });
    }

    private JLabel[] getLabels(String[] fields) {
        JLabel[] labels = new JLabel[fields.length];
        int i = 0;
        for (String f : fields) {
            JLabel l = new JLabel(f);
            labels[i] = l;
            i++;
        }
        return labels;
    }

    private JTextField[] getTextFields(String[] fields) {
        JTextField[] res = new JTextField[fields.length];
        int i = 0;
        for (String f : fields) {
            JTextField l = new JTextField();
            Dimension pref = l.getPreferredSize();
            l.setPreferredSize(new Dimension(200, pref.height));
            l.setMinimumSize(new Dimension(200, pref.height));
            l.setSize(new Dimension(200, pref.height));
            //l.setText("");
            fieldsMap.put(f, l);
            res[i] = l;
            i++;
        }
        return res;
    }

    private void addLabelTextRows(JLabel[] labels,
                                  JTextField[] textFields,
                                  Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }

    protected void addButtons(JToolBar toolBar) {
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(makeButton("SAVE", "Save", "Save"));
        toolBar.addSeparator();
        toolBar.add(makeButton("CANCEL", "Cancel", "Cancel"));
    }

    protected void addGridButtons(JToolBar toolBar) {
        toolBar.add(makeButton("ADD", "Add", "Create New"));
        toolBar.add(Box.createHorizontalGlue());
    }

    protected JButton makeButton(String actionCommand,
                                           String toolTipText,
                                           String altText) {
        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        button.setText(altText);
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private static class MyRenderer extends DefaultTableCellRenderer {

        Color backgroundColor = getBackground();

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            CellValue p = (CellValue) value;
            if(p.recentlyModified.get()) {
                c.setBackground(Color.yellow);
            } else {
                if (isSelected) {
                    Color bg = DefaultLookup.getColor(this, ui, "Table.dropCellBackground");
                    setBackground(bg == null ? table.getSelectionBackground() : bg);
                } else {
                    c.setBackground(backgroundColor);
                }
            }
            return c;
        }
    }

    private class ResetTask implements Runnable {
        private final String primaryKey;
        private final long uid;

        public ResetTask(String primaryKey, long uid) {
            this.primaryKey = primaryKey;
            this.uid = uid;
        }

        @Override
        public void run() {
            int selectedRow = table.getSelectedRow();
            model.resetHighglight(primaryKey, uid);
            if(selectedRow >= 0) {
                table.setRowSelectionInterval(selectedRow, selectedRow);
            }
        }
    }

}
