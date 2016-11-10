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
public class MenuView {

    public void showCurrentMenu(int currentMenu) {
        System.out.println("----------------------------------------------");
        if (currentMenu == 1) {
            System.out.println(   "1 Voeg nieuwe kaas toe\n"
                                + "2 Zoek kaas om te wijzigen\n"
                                + "3 Terug naar hoofdmenu");

        } else if (currentMenu == 2) {
            System.out.println(   "1 Maak nieuw account\n"
                                + "2 Zoek account om te wijzigen\n"
                                + "3 Terug naar hoofdmenu");

        } else if (currentMenu == 3) {
            System.out.println(   "1 Voeg nieuwe klant toe\n"
                                + "2 Zoek klant om te wijzigen\n"
                                + "3 Terug naar hoofdmenu");

        } else if (currentMenu == 4) {
            System.out.println(   "1 Voeg nieuwe bestelling toe\n"
                                + "2 Zoek bestelling om te wijzigen\n"
                                + "3 Terug naar hoofdmenu");

        } else {
            System.out.println(   "1 Kazen\n"
                                + "2 Accounts\n"
                                + "3 Klanten\n"
                                + "4 Bestellingen\n"
                                + "5 Afsluiten\n");
        }
    }

}
