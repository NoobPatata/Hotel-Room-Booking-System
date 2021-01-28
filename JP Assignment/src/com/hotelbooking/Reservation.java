package com.hotelbooking;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Chye Wei Lun
 */
public class Reservation implements Serializable {
    /**
     * Reservation attributes
     */
    private final String reservationID;
    private Customer customer;
    private String[] daysOfStay;
    private Room room;
    private double price;

    /**
     * Constructor
     * @param customer customer
     * @param days days
     * @param room room
     * @param reservationID reservationID
     * @param price price
     */
    Reservation(Customer customer , String[] days , Room room , String reservationID , Double price) {
        this.customer = customer;
        this.daysOfStay = days;
        this.room = room;
        this.reservationID = reservationID;
        this.price = price;
    }

    /**
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set customer
     * @param customer customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return daysOfStay
     */
    public String[] getDaysOfStay() {
        return daysOfStay;
    }

    /**
     * @param daysOfStay dayOfStay
     */
    public void setDaysOfStay(String[] daysOfStay) {
        this.daysOfStay = daysOfStay;
    }

    /**
     * @return room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return reservationID
     */
    public String getReservationID() {
        return reservationID;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}