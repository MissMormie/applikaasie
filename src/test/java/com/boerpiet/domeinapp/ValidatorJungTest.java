/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.utility.Validator;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Peaq
 */
public class ValidatorJungTest {
    
    public ValidatorJungTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Start of testclass");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("End of testclass");
    }
    
    @Before
    public void setUp() {
        System.out.println("Start of methodtest");
    }
    
    @After
    public void tearDown() {
        System.out.println("End of methodtest");
    }

    /**
     * Test of isValidInt method, of class Validator.
     */
    @Test
    public void testIsValidInt() {
        System.out.println("Validator JUnit4 test: isValidInt");
        assertFalse(Validator.isValidInt("1.89"));
        assertFalse (Validator.isValidInt("-4.12"));
        assertFalse (Validator.isValidInt("pie"));
        
        assertTrue (Validator.isValidInt("42"));
        assertTrue (Validator.isValidInt("-42"));
        assertTrue (Validator.isValidInt("3"));    
    }
    
    /**
     * Test of isValidPhonenumber method, of class Validator.
     */
    @Test
    public void testIsValidPhonenumber() {
        System.out.println("Validator JUnit4 test: isValidPhoneNumber");
        assertFalse (Validator.isValidPhonenumber("061426"));
        assertFalse (Validator.isValidPhonenumber("notAPhoneNumber"));
        assertFalse (Validator.isValidPhonenumber("-0616818106"));
        
        assertTrue (Validator.isValidPhonenumber("06-42591472"));
        
    }

    /**
     * Test of isValidEmailadres method, of class Validator.
     */
    @Test
    public void testIsValidEmailadres() {
        System.out.println("Validator JUnit4 test: isValidEmailadres");
        
        assertFalse (Validator.isValidEmailadres(" sunny but cold @ try this spot"));
        assertFalse (Validator.isValidEmailadres("my.Cat@gmail. whatisthisemail"));
        assertFalse (Validator.isValidEmailadres(" @ "));
        
        assertTrue (Validator.isValidEmailadres("thisis4real@testmail.com"));
        
    }

    /**
     * Test of isValidDouble method, of class Validator.
     */
    @Test
    public void testIsValidDouble() {
        System.out.println("Validator JUnit4 test: isValidDouble");
        String input = "";
        boolean expResult = false;
        boolean result = Validator.isValidDouble(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidDate method, of class Validator.
     */
    @Ignore
    @Test(expected = ParseException.class) //only works if the tested method throws the exception, and doesn't
                                           //use try/catch as it does now
    public void testIsValidDate() {
        System.out.println("Validator JUnit4 test expected exception: isValidDate");
        String date = " 16";
        System.out.println("Expecting parseexception: " + Validator.isValidDate(date));        
    }
    
    @Test
    public void testIsValidDate2 () {
        String date = "-15*48-12";
        boolean expResult = false;
        boolean result = Validator.isValidDate(date);
        assertEquals (expResult, result);
    }

    /**
     * Test of isValidPostcode method, of class Validator.
     */
    @Test(timeout = 500)
    public void testIsValidPostcode() {
        System.out.println("Validator JUnit4 test timeout: isValidPostcode");
        String zip = "2351TY";
        System.out.println("Time to check methodtime when checking a postcode ");
        System.out.println(Validator.isValidPostcode(zip));
        
    }  
}