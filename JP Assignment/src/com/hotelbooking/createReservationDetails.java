package com.hotelbooking;

import javax.swing.*;
import java.awt.event.*;

public class createReservationDetails extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    public JTextField txtDays;
    public JTextField txtTaxes;
    public JTextField txtTourismTax;
    public JTextField txtTotal;
    public JTextField txtRoomCost;

    public createReservationDetails() {
        txtDays.setEditable(false);
        txtTaxes.setEditable(false);
        txtTourismTax.setEditable(false);
        txtTotal.setEditable(false);
        txtRoomCost.setEditable(false);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    public void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
