/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.domeinapp.MenuModel;
import com.boerpiet.utility.ConsoleInput;
import com.boerpiet.viewapp.LoginView;
import com.boerpiet.viewapp.MenuView;

/**
 *
 * @author Sonja
 */
public class LoginController {
    AccountModel accountModel;
    LoginView loginView;

    public LoginController(AccountModel accountModel, LoginView loginView) {
        this.accountModel = accountModel;
        this.loginView = loginView;
        login();
    }
 
    private void login() {
        loginView.showLogin();
        
        String usernamePassword = listenForLogin();
        if(usernamePassword.equalsIgnoreCase("exit") || usernamePassword.equals("9"))
            return;

        String[] parts = usernamePassword.split(" ");
        if (parts.length < 2) {
            loginView.showLoginFailed();
            login();
            return;
        }        

        if (accountModel.validateLogin(parts[0], parts[1])) {
            loginView.showLoginSuccess();
            MenuController mc = new MenuController(new MenuModel(accountModel.getLogin()), new MenuView());
            mc.showMenu();
            
            loginView.showLogoutSuccess();
            login();
        } else {
            loginView.showLoginFailed();
            login();
        }
    }
        
    private String listenForLogin() {
        String usernamePassword = ConsoleInput.textInput();
        if(usernamePassword.isEmpty())
            return listenForLogin();
        return usernamePassword;
    }    
}
