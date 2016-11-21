/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

/**
 *
 * @author Sonja
 */
public class Validator {
    public static boolean isValidInt(String input) {   
        try {
           int inputInt = Integer.parseInt(input);
           return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public static boolean isValidPhonenumber(String phoneNumber) {
        // Taken from stack overflow, don't remember exact post.
        String regex = "^(?:0|(?:\\+|00) ?31 ?)(?:(?:[1-9] ?(?:[0-9] ?){8})|"
                + "(?:6 ?-? ?[1-9] ?(?:[0-9] ?){7})|"
                + "(?:[1,2,3,4,5,7,8,9]\\d ?-? ?[1-9] ?(?:[0-9] ?){6})|"
                + "(?:[1,2,3,4,5,7,8,9]\\d{2} ?-? ?[1-9] ?(?:[0-9] ?){5}))$";
        return phoneNumber.matches(regex);
    }
    
    /**
     * Checks if the string has a non-whitespaces @ non-whitespace . non-whitespace
     * @param email
     * @return true on valid 
     */
    public static boolean isValidEmailadres(String email) {
        // wrote it myself, regex = hard
        String regex ="\\S+@\\S+\\.\\S{2,}";
        return email.matches(regex);
    }

    public static boolean isValidPostcode(String postcode) {
        String regex ="[1-9][0-9]{3}\\s?\\w\\w";
        return postcode.matches(regex);
    }
}
