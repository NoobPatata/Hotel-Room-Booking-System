package com.hotelbooking;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class createReservation extends JFrame {
    private JTextField txtNRIC;
    private JTextField txtEmail;
    private JTextField txtName;
    private JTextField txtContact;
    private JLabel lblName;
    private JLabel lblContact;
    private JLabel lblIC;
    private JCheckBox mondayCheckBox;
    private JCheckBox wednesdayCheckBox;
    private JCheckBox fridayCheckBox;
    private JCheckBox sundayCheckBox;
    private JCheckBox tuesdayCheckBox;
    private JCheckBox thursdayCheckBox;
    private JCheckBox saturdayCheckBox;
    private JButton createReservationButton;
    private JComboBox cmbRooms;
    private JPanel createReservation;
    private JPanel daysPanel;
    private JButton btnShow;
    private JButton btnBack;

    public createReservation() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        add(createReservation);
        setTitle("Create Reservation");
        setSize(800 , 395);
        DataManager dataManager = new DataManager();
        ReservationManager reservationManager = new ReservationManager(dataManager);

        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtName.getText().isEmpty() || txtContact.getText().isEmpty() || txtNRIC.getText().isEmpty() || txtEmail.getText().isEmpty() || getSelectedDays().isEmpty() ) {
                    JOptionPane.showMessageDialog(null , "Please fill in all the required fields");
                } else if(!validateContact()) {
                    JOptionPane.showMessageDialog(null , "Invalid contact number format. Please follow the format 01112345678");
                } else if(!validateEmail()) {
                    JOptionPane.showMessageDialog(null , "Invalid email format.");
                } else if(!validateNRIC()) {
                    JOptionPane.showMessageDialog(null, "Invalid NRIC format. Please follow the format XXXXXXXXXXXXX");
                } else if (cmbRooms.getSelectedIndex() == -1 ) {
                    JOptionPane.showMessageDialog(null, "Please select a room");
                }else {
                    Customer customer = new Customer(txtName.getText(), txtNRIC.getText(), txtContact.getText(), txtEmail.getText());
                    Room room = new Room(cmbRooms.getSelectedItem().toString());
                    String[] days = getSelectedDays().toArray(new String[0]);
                    reservationManager.createReservation(customer, days, room , getTotalCost());
                    showCost();
                    JOptionPane.showMessageDialog(null, "Reservation created successfully.");
                    onCancel();
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        cmbRooms.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                List<String> days = getSelectedDays();
                if(days.isEmpty()) {
                    cmbRooms.removeAllItems();
                    JOptionPane.showMessageDialog(null, "Please select the day(s) of reservation.");
                } else {
                    List<String> selectedRoom = getRoomsWithSameDays(days);
                    List<String> allRooms = getAllRoomID();
                    allRooms.removeAll(new HashSet(selectedRoom));
                    cmbRooms.setModel(new DefaultComboBoxModel<String>(allRooms.toArray(new String[0])));
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
    }


    /**
     * Dispose current form and call mainPage
     */
    private void onCancel() {
        dispose();
        MainPage mainPage = new MainPage();
        mainPage.setLocationRelativeTo(null);
        mainPage.setVisible(true);
    }


    DataManager dataManager = new DataManager();
    ReservationManager reservationManager = new ReservationManager(dataManager);

    /**
     * @return is valid email
     */
    private boolean validateEmail() {
        Validation validation = new Validation();
        return validation.isValidEmail(txtEmail.getText());
    }

    /**
     * @return is valid contact
     */
    private boolean validateContact() {
        Validation validation = new Validation();
        return validation.isValidContact(txtContact.getText());
    }

    /**
     * @return is valid IC
     */
    private boolean validateNRIC() {
        Validation validation = new Validation();
        return validation.isValidNRIC(txtNRIC.getText());
    }

    /**
     * Show cost form
     */
    private void showCost() {
        createReservationDetails createReservationDetails = new createReservationDetails();
        createReservationDetails.setSize(400 , 300);
        createReservationDetails.setLocationRelativeTo(null);
        createReservationDetails.txtDays.setText(listToString(getSelectedDays()));
        createReservationDetails.txtTourismTax.setText(Double.toString(getTourismTax()));
        createReservationDetails.txtTaxes.setText(Double.toString(getTaxes()));
        createReservationDetails.txtTotal.setText(Double.toString(getTotalCost()));
        createReservationDetails.txtRoomCost.setText(Double.toString(getCostOfRoom()));
        createReservationDetails.setVisible(true);
    }

    /**
     * @return list of selectedDays
     */
    private List<String> getSelectedDays() {
        List<JCheckBox> checkBoxes =  getComponentCheckBox();
        List<String> days = new ArrayList<String>();
        for (JCheckBox checkBox : checkBoxes) {
            if(checkBox.isSelected()) {
                days.add(checkBox.getText());
            }
        }
        return days;
    }

    /**
     * @return list of checkboxes
     */
    private List<JCheckBox> getComponentCheckBox() {
        List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
        for(Component component : daysPanel .getComponents()) {
            if(component instanceof JCheckBox) checkboxes.add((JCheckBox)component);
        }
        return checkboxes;
    }

    /**
     * @param list list
     * @return new list after removing duplicate
     */
    private List<String> removeDuplicates(List<String> list) {
        List<String> newList = new ArrayList<String>();
        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * @param days list of days
     * @return list of reserved rooms that has been booked on the selected day(s)
     */
    private List<String>  getRoomsWithSameDays(List<String> days) {
        ArrayList<Reservation> reservations =  reservationManager.getReservations();
        List<String> bookedRoom = new ArrayList<>();
        if(reservations != null) {
            for (Reservation reservation : reservations) {
                List<String> list = Arrays.asList(reservation.getDaysOfStay());
                for(String day : days) {
                    if (list.contains(day)) {
                        bookedRoom.add(reservation.getRoom().getRoomID());
                    }
                }
            }
        }

        return removeDuplicates(bookedRoom);
    }

    /**
     * @return list of all rooms
     */
    private List<String> getAllRoomID() {
        ArrayList<Room> rooms = (ArrayList) dataManager.readSerializedObjectList("rooms");
        List<String> roomId = new ArrayList<>();
        for (Room room : rooms) {
            roomId.add(room.getRoomID());
        }
        return  roomId;
    }

    /**
     * @return total cost
     */
    private double getTotalCost() {
        return  getCostOfRoom() + getTaxes() + getTourismTax();
    }

    /**
     * @return cost of room
     */
    private double getCostOfRoom() {
        return 350 * getSelectedDays().size();
    }

    /**
     * @return tax amount
     */
    private double getTaxes() {
        return getCostOfRoom() * 0.1;
    }

    /**
     * @return tourism tax amoint
     */
    private double getTourismTax() {
        return  getSelectedDays().size() * 10;
    }

    /**
     * @param list list
     * @return string of list
     */
    private String listToString(List<String> list) {
        StringBuilder strbul = new StringBuilder();
        for(String str : list)
        {
            strbul.append(str);
            //for adding comma between elements
            strbul.append(",");
        }
        return strbul.toString();
    }
}
