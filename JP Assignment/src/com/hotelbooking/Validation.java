package com.hotelbooking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    boolean isValidContact(String contact) {
        Pattern pattern = Pattern.compile("^(01)[0-46-9]*[0-9]{7,8}$");
        Matcher matcher = pattern.matcher(contact);
        return (matcher.find() && matcher.group().equals(contact));
    }

    boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean isValidNRIC(String NRIC) {
        Pattern pattern = Pattern.compile("([0-9][0-9])((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))([0-9][0-9])([0-9][0-9][0-9][0-9])");
        Matcher matcher = pattern.matcher(NRIC);
        return matcher.matches();
    }

}
