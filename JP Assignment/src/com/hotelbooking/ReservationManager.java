package com.hotelbooking;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

/**
 * @author Chye Wei Lun
 */
public class ReservationManager  {

    private final DataManager dataManager;
    private final String filename;
    ArrayList<Reservation> reservations;

    /**
     * Constructor
     * @param dataManager Datamanager
     */
    ReservationManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.filename = filename();
    }

    /**
     * @param customer Customer Obj
     * @param days Array of days
     * @param room Room arraylist
     */
    void createReservation(Customer customer, String[] days, Room room, double price) {
        ArrayList<Reservation> reservations = getReservations();
        if(reservations == null) {
            reservations = new ArrayList<>();
        }
        Reservation obj = new Reservation(customer, days, room , generateID() , price);
        reservations.add(obj);
        dataManager.writeSerializedObjectList(filename, reservations);
    }

    /**
     * Remove an reservation based on reservationID
     * @param reservationID reservationID
     */
    void removeReservation(String reservationID) {
         ArrayList<Reservation> reservations = getReservations();
         if (reservations != null) {
             Iterator iterator =reservations.iterator();
             while (iterator.hasNext()) {
                 Reservation reservation = (Reservation)iterator.next();
                 if (reservation.getReservationID().equals(reservationID)) iterator.remove();
             }
         }
         dataManager.writeSerializedObjectList(this.filename , reservations);
    }

    /**
     * Modify selected reservation
     * @param reservationID reservation ID
     * @param room booked room
     * @param days day(s) of stay
     */
    void modifyReservation(String reservationID , Room room , String[] days , double price) {
        ArrayList<Reservation> reservations = getReservations();
        if (reservations != null) {
            for (Reservation reservation : reservations) {
                if(reservation.getReservationID().equals(reservationID)) {
                    reservation.setRoom(room);
                    reservation.setDaysOfStay(days);
                    reservation.setPrice(price);
                }
            }
        }
        dataManager.writeSerializedObjectList(this.filename , reservations);
    }

    /**
     * @return Arraylist of reservations
     */
    public ArrayList<Reservation> getReservations() {
        this.reservations = (ArrayList) dataManager.readSerializedObjectList(filename());
        return reservations;
    }

    /**
     * @return filename of the week's reservation
     */
    public String filename() {
        String filename = "Booking\\" + getFirstDateOfWeek();
        return filename;
    }

    /**
     * @return date of first day of the week
     */
    String getFirstDateOfWeek() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        LocalDate date = now.with(fieldISO, 1);
        return date.toString();
    }

    void createFolder() throws IOException {
        Path path = Paths.get("booking");
        Files.createDirectories(path);
    }

    /**
     * @return random integer for reservation ID
     */
    String generateID() {
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int randomNumber = random.nextInt(max + 1 - min) + min;
        return Integer.toString(randomNumber);
    }


}
