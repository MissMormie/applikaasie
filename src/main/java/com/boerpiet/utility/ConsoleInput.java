/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.utility;

import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class ConsoleInput {
    
    
    public static String textInput() {
        Scanner input = new Scanner(System.in);
        String text = input.nextLine();
        return text;
    }
    
    public static String notEmptyTextInput() {
        Scanner input = new Scanner(System.in);
        String text = input.nextLine();
        if(text.isEmpty())
            return notEmptyTextInput();
        return text;        
    }
    
    /**
     * 
     * @return 
     */
    public static Integer numberInput() {
        Scanner input = new Scanner(System.in);
        String number = input.nextLine();
        if(Validator.isValidInt(number))
            return Integer.parseInt(number);
        
        return null;
    }    
    
    /**
     * Gives back valid type of nl phonenumber or empty String.
     * @return 
     */
    public static String phoneInput() {
        Scanner input = new Scanner(System.in);
        String phone = input.nextLine();
        if(phone.equals("") || Validator.isValidPhonenumber(phone))
            return phone;
        
        return null;
    }
    
    /**
     * Return either valid type of email adres or empty string.
     * @return 
     */
    public static String emailInput() {
        Scanner input = new Scanner(System.in);
        String email = input.nextLine();
        if (email.equals("") || Validator.isValidEmailadres(email))
            return email;
        
        return null;
    }
    
    /**
     * Gives back valid postcode input.
     * @return 
     */
    public static String postcodeInput() {
        Scanner input = new Scanner(System.in);

        String postcode = input.nextLine();
        if (Validator.isValidPostcode(postcode))
            return postcode;

        return null;
    }
}
