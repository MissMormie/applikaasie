/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.*;
import com.boerpiet.domeinapp.MenuModel;
import java.util.Scanner;
import com.boerpiet.viewapp.MenuView;

/**
 *
 * @author Sonja
 */
public class MenuController {

    MenuModel menuModel;
    MenuView menuView;
    Scanner input = new Scanner(System.in);

    public MenuController(MenuModel menuPojo, MenuView menuView) {
        this.menuModel = menuPojo;
        this.menuView = menuView;
        showMenu();
    }

    private void showMenu() {
        menuView.showCurrentMenu(menuModel.getCurrentMenu());
        menuModel.changeCurrentMenu(listenForMenuInput());
        showMenu();
    }

    // todo catch exception for wrong input
    private int listenForMenuInput() {
        int menuChoice;
        try {
            menuChoice = input.nextInt();
        } catch (Exception ex) {
            menuChoice = menuModel.getCurrentMenu();
        }
        return menuChoice;
    }

}
