/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.MenuModel;
import com.boerpiet.utility.ConsoleInput;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.MenuView;

/**
 *
 * @author Sonja
 */
public class MenuController {

    // ------------ VARIABLES ---------------------------------

    MenuModel menuModel;
    MenuView menuView;

    // ------------ CONSTRUCTORS ---------------------------------

    public MenuController(MenuModel menuPojo, MenuView menuView) {
        this.menuModel = menuPojo;
        this.menuView = menuView;
    }

    // ------------ PRIVATE FUNCTIONS ---------------------------------
    
    public void showMenu() {
        menuView.showMenu(menuModel.getMenuNode());
        listenForXMLMenuInput();
    }

    private void listenForXMLMenuInput() {
        int menu = numberListener();
        
        menuModel.chooseMenuItem(menu);
        if (menuModel.isLoggedIn() == true)
            showMenu();
    }

    private int numberListener() {
        String number = ConsoleInput.textInput();
        if(Validator.isValidInt(number))
            return Integer.parseInt(number);
        
        menuView.showValidNumber();
        return numberListener();
    }        
}
