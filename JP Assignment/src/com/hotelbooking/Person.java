package com.hotelbooking;

import java.io.Serializable;

/**
 * @author Chye Wei Lun
 */
public class Person implements Serializable {
    /**
     * Person attribute
     */
    private final String name;

    /**
     * Constructor
     * @param name name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }
}
