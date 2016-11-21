/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

/**
 *
 * @author Sonja
 */
public class LoginView {
    public void showDivider() {
        System.out.println("\n------------------------------------------------------------------------\n");        

    }
    
    public void showLogin() {
        System.out.println("Voer 'gebruikersnaam <spatie> wachtwoord' in. Of type exit of 9 om te stoppen.");
    }
   
    public void showLoginFailed() {
        System.out.println("De combinatie van gebruiksnaam en wachtwoord bestaat niet.");
    }
    
    public void showLoginSuccess() {
        System.out.println("\nLogin geslaagd.");
    }

    public void showLogoutSuccess() {
        System.out.println("Logout geslaagd.");
    }
}
