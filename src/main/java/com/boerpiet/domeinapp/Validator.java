/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Sonja
 */
public class Validator {
        
    public static boolean isValidInt(String input) {   
        try {
           int inputInt = Integer.parseInt(input);
           if (inputInt > 0) {
               return true;
           } else {
               return false;
           }
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
    
    public static boolean isValidDouble (String input) {
        try {
            double inputDouble = Double.parseDouble (input);
            if (inputDouble > 0) {
                return true;
            } else {
            return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public static boolean isValidDate (String input) {
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
            Date date = sdf.parse(input);
            if (!input.equals(sdf.format(date))) {
                return false;
            }
        } catch (ParseException ex) {
                    ex.printStackTrace();
            }
    return true;
    }
}
