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
    public void showLogin() {
        System.out.println("Enter your username, space, then enter your password.");
    }
   
    public void showLoginFailed() {
        System.out.println("The combination of username and password does not exist");
    }
    
    public void showLoginSuccess() {
        System.out.println("Login succesfull");
    }
    
}
