/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.*;
import com.boerpiet.domeinapp.MenuModel;
import com.boerpiet.utility.Validator;
import java.util.Scanner;
import com.boerpiet.viewapp.MenuView;

/**
 *
 * @author Sonja
 */
public class MenuController {

    // ------------ VARIABLES ---------------------------------

    MenuModel menuModel;
    MenuView menuView;
    Scanner input = new Scanner(System.in);

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
        String number = input.nextLine();
        if(Validator.isValidInt(number))
            return Integer.parseInt(number);
        
        return numberListener();
    }        
    
}
