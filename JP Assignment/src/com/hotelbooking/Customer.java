package com.hotelbooking;

import java.io.Serializable;

/**
 * @author Chye Wei Lun
 */
public class Customer extends Person implements Serializable  {
    /**
     * Customer attributes
     */
    private String nric, contactNumber , email;

    /**
     * Constructor
     * @param name name
     * @param nric nric
     * @param contactNumber contact number
     * @param email email
     */
    public Customer(String name ,String nric, String contactNumber, String email) {
        super(name);
        this.nric = nric;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    /**
     * @return contact number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber contact number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * @param nric nric
     */
    public void setNric(String nric) {
        this.nric = nric;
    }
}
