package com.hotelbooking;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class EditReservation extends JFrame {
    public JTextField txtNRIC;
    private JLabel lblIC;
    public JTextField txtEmail;
    private JLabel lblName;
    private JLabel lblContact;
    public JTextField txtName;
    public JTextField txtContact;
    public JComboBox cmbRooms;
    private JButton btnSave;
    private JButton btnBack;
    private JPanel daysPanel;
    private JCheckBox mondayCheckBox;
    private JCheckBox wednesdayCheckBox;
    private JCheckBox fridayCheckBox;
    private JCheckBox sundayCheckBox;
    private JCheckBox tuesdayCheckBox;
    private JCheckBox thursdayCheckBox;
    private JCheckBox saturdayCheckBox;
    private JPanel editReservation;
    public JTextField txtReservationID;

    /**
     * form constructor
     */
    public EditReservation() {
        txtReservationID.setEditable(false);
        txtNRIC.setEditable(false);
        txtEmail.setEditable(false);
        txtName.setEditable(false);
        txtContact.setEditable(false);
        setContentPane(editReservation);
        setTitle("Edit Reservation");
        setLocationRelativeTo(null);
        setSize(600 , 400);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtName.getText() == null || txtContact.getText() == null || txtNRIC.getText() == null || txtEmail.getText() == null || getSelectedDays().isEmpty() ) {
                    JOptionPane.showMessageDialog(null , "Please fill in all the required fields");
                } else if(!validateContact()) {
                    JOptionPane.showMessageDialog(null , "Invalid contact number format. Please follow the format 01112345678");
                } else if(!validateEmail()) {
                    JOptionPane.showMessageDialog(null , "Invalid email format.");
                } else if(!validateNRIC()) {
                    JOptionPane.showMessageDialog(null, "Invalid NRIC format. Please follow the format XXXXXXXXXXXXX");
                } else {
                DataManager dataManager = new DataManager();
                ReservationManager reservationManager = new ReservationManager(dataManager);
                Room room = new Room(cmbRooms.getSelectedItem().toString());
                String[] days = getSelectedDays().toArray(new String[0]);
                reservationManager.modifyReservation(txtReservationID.getText() , room , days, getTotalCost());
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
        return validation.isValidContact(txtNRIC.getText());
    }

    /**
     * @return list of checkboxes
     */
    public List<JCheckBox> getComponentCheckBox() {
        List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
        for(Component component : daysPanel .getComponents()) {
            if(component instanceof JCheckBox) checkboxes.add((JCheckBox)component);
        }
        return checkboxes;
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
     * @param days days
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
     * @param list list
     * @return list without duplicate
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
     * showing cost dialog class
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
     * @return total cost of reservation
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
     * @return tourism tax amount
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
