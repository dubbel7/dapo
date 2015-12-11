package org.dubbel7.dapo.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractPanel extends JPanel implements ActionListener {

    protected JTextArea textArea;
    protected String newline = "\n";

    static final private String ACTION = "action";

    private final Map<String, JTextField> fieldsMap = new HashMap<>();

    public AbstractPanel(String name, String[] fields) {
        super(new BorderLayout());


        //fields

        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        textControlsPane.setLayout(gridbag);

        JLabel[] labels = getLabels(fields);
        JTextField[] textFields = getTextFields(fields);
        addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        textControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Fields"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JToolBar toolBar = new JToolBar("MX");
        addButtons(name, toolBar);

        //Create the text area used for output.  Request
        //enough space for 5 rows and 30 columns.
        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Lay out the main panel.
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        JPanel fieldsPane = new JPanel(new BorderLayout());
        fieldsPane.add(textControlsPane, BorderLayout.PAGE_START);
        fieldsPane.add(toolBar, BorderLayout.PAGE_END);

        JPanel logPane = new JPanel(new BorderLayout());
        logPane.add(scrollPane, BorderLayout.CENTER);

        add(fieldsPane, BorderLayout.EAST);
        add(logPane, BorderLayout.CENTER);
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


    protected void addButtons(String name, JToolBar toolBar) {
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(makeNavigationButton(ACTION, name, name));
    }


    private void addLabelTextRows(JLabel[] labels,
                                  JTextField[] textFields,
                                  GridBagLayout gridbag,
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

    protected JButton makeNavigationButton(String actionCommand,
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
        String cmd = e.getActionCommand();

        if (ACTION.equals(cmd)) { // second button clicked
            displayResult("Result: " + doit());
        }
    }

    protected abstract String doit();


    protected void displayResult(String actionDescription) {
        textArea.append(actionDescription + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    protected String getValue(String field) {
        return fieldsMap.get(field).getText();
    }

}
