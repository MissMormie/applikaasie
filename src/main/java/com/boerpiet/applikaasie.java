/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet;

import com.boerpiet.controllerapp.LoginController;
import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.viewapp.LoginView;

/**
 *
 * @author Sonja
 */
public class applikaasie {
    public static void main(String[] args) {
        new LoginController(new AccountModel(), new LoginView());
    }
}
