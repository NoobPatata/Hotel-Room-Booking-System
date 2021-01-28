package com.hotelbooking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Receipt extends JFrame {
    public JTextField txtReservationID;
    public JTextField txtRoom;
    public JTextField txtNights;
    public JTextField txtAmount;
    public JTextField txtTax;
    public JTextField txtTourismTax;
    public JTextField txtTotal;
    private JPanel receipt;
    private JButton btnExit;

    public Receipt() {
        setContentPane(receipt);
        setTitle("Receipt");
        setLocationRelativeTo(null);
        setSize(600 , 400);

        txtTourismTax.setEditable(false);
        txtTotal.setEditable(false);
        txtRoom.setEditable(false);
        txtNights.setEditable(false);
        txtAmount.setEditable(false);
        txtReservationID.setEditable(false);
        txtTax.setEditable(false);


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onCancel() {
        dispose();
        MainPage mainPage = new MainPage();
        mainPage.setLocationRelativeTo(null);
        mainPage.setVisible(true);
    }

}
