package com.hotelbooking;

/**
 * @author Chye Wei Lun
 */
public class Staff extends Person{
    /**
     * Staff attributes
     */
    private final String username;
    private final String password ;


    /**
     * Staff constructor
     */
    public Staff(String name) {
        super(name);
        this.username = "admin";
        this.password = "p@$$w0rd";
    }

    /**
     * login function
     * @param username
     * @param password
     * @return
     */
    boolean login(String username , String password) {
        return username.equals(this.username) && password.equals(this.password);
    }
}
