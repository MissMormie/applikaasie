/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

/**
 *
 * @author Peaq
 */
public class BestellingView {
            
    public void showNewBestelling () {
        
        System.out.println("Wil je een nieuwe bestelling invoeren? (Y/N):");        
    }
    
    public void showNewBestellingSucces () {
        System.out.println("Nieuwe bestelling is aangemaakt.");
    }
    
    public void showNewBestellingFailure () {
        System.out.println("Er is iets misgegaan, probeer het opnieuw");
    }

    public void startModifyOrder() {
        System.out.println("Je bent nu in het bestelling-wijzigingsmenu.");
        System.out.println("Wat wil je doen:\n"
                +"1. Soort bestelde kaas wijzigen \n"
                +"2. Aantal bestelde kazen wijzigen \n"
                +"3. Terug naar het menu");
    }
}