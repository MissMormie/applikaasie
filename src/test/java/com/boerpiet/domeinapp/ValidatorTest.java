/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sonja
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    // --------------------- isValidInt() Tests -----------------------------
    /**
     * Test of isValidInt method, of class Validator.
     */
    @Test
    public void testIsValidInt() {
        System.out.println("isValidInt Enter");
        String input = "\n";
        boolean expResult = false;
        boolean result = Validator.isValidInt(input);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsValidInt2() {
        System.out.println("isValidInt String");
        String input = "as";
        boolean expResult = false;
        boolean result = Validator.isValidInt(input);
        assertEquals(expResult, result);        
    }

    @Test
    public void testIsValidInt3() {
        System.out.println("isValidInt int");
        String input = "27";
        boolean expResult = true;
        boolean result = Validator.isValidInt(input);
        assertEquals(expResult, result);        
    }

    // --------------------- isValidPhonenumber() Tests ---------------------
    
    /**
     * Test of isValidPhonenumber method, of class Validator.
     */
    @Test
    public void testIsValidPhonenumber() {
        System.out.println("isValidPhonenumber");
        String phoneNumber = "06-24137547";
        boolean expResult = true;
        boolean result = Validator.isValidPhonenumber(phoneNumber);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsValidPhonenumber2() {
        System.out.println("isValidPhonenumber");
        String phoneNumber = "+31 6-24137547";
        boolean expResult = true;
        boolean result = Validator.isValidPhonenumber(phoneNumber);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsValidPhonenumber3() {
        System.out.println("isValidPhonenumber");
        String phoneNumber = "023456789";
        boolean expResult = false;
        boolean result = Validator.isValidPhonenumber(phoneNumber);
        assertEquals(expResult, result);
    } 
    
    @Test
    public void testIsValidPhonenumber4() {
        System.out.println("isValidPhonenumber");
        String phoneNumber = "abcdef";
        boolean expResult = false;
        boolean result = Validator.isValidPhonenumber(phoneNumber);
        assertEquals(expResult, result);
    }    
    
    
    // --------------------- isValidEmailadres() Tests -----------------------------
    
    /**
     * Test of isValidEmailadres method, of class Validator.
     */
    @Test
    public void testIsValidEmailadres() {
        System.out.println("isValidEmailadres");
        String email = "sonja@sonja.nl";
        boolean expResult = true;
        boolean result = Validator.isValidEmailadres(email);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsValidEmailadres2() {
        System.out.println("isValidEmailadres");
        String email = "sonja@sonja";
        boolean expResult = false;
        boolean result = Validator.isValidEmailadres(email);
        assertEquals(expResult, result);
    }

    
    @Test
    public void testIsValidEmailadres3() {
        System.out.println("isValidEmailadres");
        String email = "sonja.sonja";
        boolean expResult = false;
        boolean result = Validator.isValidEmailadres(email);
        assertEquals(expResult, result);
    }
        
}
