package com.hotelbooking;

import java.io.Serializable;

/**
 * @author Chye Wei Lun
 */
public class Room implements Serializable {
    /**
     * Room attributes
     */
    public String roomID;
    public double price = 350.00;

    /**
     * Constructor
     * @param roomID roomID
     */
    public Room(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return roomID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Set roomID
     * @param roomID roomID
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", price=" + price + '}';
    }
}
