/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.controllerapp.AccountController;
import com.boerpiet.viewapp.AccountView;

/**
 *
 * @author Sonja
 */
public class MenuModel {

    int currentMenu = -1;

    public int getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(int currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void changeCurrentMenu(int changeMenu) {
        switch(currentMenu) {
            case 1: // Kazen
                pickFromCheeseMenu(changeMenu);
                break;
            case 2: // Account
                pickFromAccountMenu(changeMenu);
                break;
            case 3: // KLanten
                pickFromKlantenMenu(changeMenu);
                break;
            case 4:  // bestellingen
                pickFromBestellingMenu(changeMenu);
                break;
            case 5: 
                System.exit(0);
            default: 
                currentMenu = changeMenu;
        }
    }

    private void pickFromCheeseMenu(int changeMenu) {
        switch (changeMenu) {
            case 1: 
                break;
            case 2:
                break;
            case 3: 
                currentMenu = 0;
        }
    }

    private void pickFromAccountMenu(int changeMenu) {
        AccountController ac;
        switch(changeMenu) {
            case 1: // New Account
                ac = new AccountController(new AccountModel(), new AccountView());
                ac.newAccount();
                break;
            case 2: // Change Account
                ac = new AccountController(new AccountModel(), new AccountView());
                ac.selectAccountToModify();
                break;
            case 3: // Delete Account
                ac = new AccountController(new AccountModel(), new AccountView());
                ac.deleteAccount();
                break;
            case 4:
                currentMenu = 0;
                break;
        }
    }

    private void pickFromKlantenMenu(int changeMenu) {
        if (changeMenu == 1) {

        } else if (changeMenu == 2) {

        } else if (changeMenu == 3) {
            currentMenu = 0;
        }
    }

    private void pickFromBestellingMenu(int changeMenu) {
        if (changeMenu == 1) {

        } else if (changeMenu == 2) {

        } else if (changeMenu == 3) {
            currentMenu = 0;
        }
    }
}
