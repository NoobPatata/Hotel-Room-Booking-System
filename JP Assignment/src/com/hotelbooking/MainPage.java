package com.hotelbooking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainPage extends JFrame{
    private JPanel MainPage;
    private JButton btnCreate;
    private JTextField txtSearch;
    private JTable tblBooking;
    private JButton btnRemove;
    private JButton btnView;
    private JButton btnReceipt;
    private JButton btnLogout;


    /**
     * Form constructor
     */
    public MainPage() {

        add(MainPage);
        createTable();
        addData();
        setTitle("Hotel Booking System");
        setSize(700 , 600);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManager dataManager = new DataManager();
                ReservationManager reservationManager = new ReservationManager(dataManager);
                int column = 0;
                int row = tblBooking.getSelectedRow();
                if ( row != -1 ) {
                    String value = tblBooking.getModel().getValueAt(row , column).toString();
                    reservationManager.removeReservation(value);
                    DefaultTableModel model = (DefaultTableModel) tblBooking.getModel();
                    if (reservationManager.getReservations() == null) {
                        model.setRowCount(0);
                    } else {
                        model.setRowCount(0);
                        addData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null , "Please select a reservation.");
                }

            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                createReservation createReservation = new createReservation();
                createReservation.setLocationRelativeTo(null);
                createReservation.setVisible(true);
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String value = txtSearch.getText().toLowerCase();
                search(value);
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationDetails();
            }
        });

        btnReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReceipt();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    DataManager dataManager = new DataManager();
    ReservationManager reservationManager = new ReservationManager(dataManager);

    /**
     * Show reservation details
     */
    private void showReservationDetails() {
        ArrayList<Reservation> reservations = reservationManager.getReservations();
        int column = 0;
        int row = tblBooking.getSelectedRow();
        if ( row != -1 ) {
            String value = tblBooking.getModel().getValueAt(row , column).toString();
            EditReservation editReservation = new EditReservation();
            List<JCheckBox> checkBoxes =  editReservation.getComponentCheckBox();
            editReservation.txtReservationID.setText(value);
            for(Reservation reservation : reservations) {
                if (reservation.getReservationID().equals(value)) {
                    editReservation.txtContact.setText(reservation.getCustomer().getContactNumber());
                    editReservation.txtEmail.setText(reservation.getCustomer().getEmail());
                    editReservation.txtName.setText(reservation.getCustomer().getName());
                    editReservation.txtNRIC.setText(reservation.getCustomer().getNric());
                    editReservation.cmbRooms.addItem(reservation.getRoom().getRoomID());
                    for (JCheckBox checkBox : checkBoxes) {
                        for(String day : reservation.getDaysOfStay()) {
                            if (checkBox.getText().equals(day)) checkBox.setSelected(true);
                        }
                    }
                }
            }
            setVisible(false);
            editReservation.setLocationRelativeTo(null);
            editReservation.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null , "Please select a reservation.");
        }
    }

    /**
     * Show reservation receipt
     */
    private void showReceipt() {
        ArrayList<Reservation> reservations = reservationManager.getReservations();
        int column = 0;
        int row = tblBooking.getSelectedRow();
        if( row != -1 ) {
            String value = tblBooking.getModel().getValueAt(row , column).toString();
            Receipt receipt = new Receipt();
            receipt.txtReservationID.setText(value);
            for(Reservation reservation : reservations) {
                if (reservation.getReservationID().equals(value)) {
                    int numofDays = reservation.getDaysOfStay().length;
                    double costOfRoom = 350 * numofDays;
                    double tourismTax = numofDays * 10;
                    double tax = costOfRoom * 0.1;
                    receipt.txtAmount.setText(Double.toString(reservation.getPrice()));
                    receipt.txtNights.setText(Integer.toString(numofDays));
                    receipt.txtRoom.setText(reservation.getRoom().getRoomID());
                    receipt.txtTax.setText(Double.toString(tax));
                    receipt.txtTourismTax.setText(Double.toString(tourismTax));
                    receipt.txtTotal.setText(Double.toString(costOfRoom));
                }
            }
            setVisible(false);
            receipt.setLocationRelativeTo(null);
            receipt.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null , "Please select a reservation.");
        }
    }

    /**
     * function to be called when cross icon is clicked
     */
    private void onCancel() {
        dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }

    /**
     * Search method
     * @param query Customer name or Reservation ID
     */
    private void search(String query) {
        DefaultTableModel model = (DefaultTableModel) tblBooking.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblBooking.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    /**
     * Initialize Jtable
     */
    private void createTable() {

        tblBooking.setModel(new DefaultTableModel(
                null,
                new String[] {"Reservation ID" , "Customer Name" , "Amount"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    /**
     * Add row to Jtable
     */
    private void addData() {
        DataManager dataManager = new DataManager();
        ReservationManager reservationManager = new ReservationManager(dataManager);
        ArrayList<Reservation> reservations = reservationManager.getReservations();
        DefaultTableModel model = (DefaultTableModel) tblBooking.getModel();
        Object[] data = new Object[3];
        if(reservations != null ) {
            Iterator itr = reservations.iterator();
            while (itr.hasNext()) {
                Reservation reservation = (Reservation)itr.next();
                data[0] = reservation.getReservationID();
                data[1] = reservation.getCustomer().getName();
                data[2] = reservation.getPrice();
                model.addRow(data);
            }
        }

    }



}
