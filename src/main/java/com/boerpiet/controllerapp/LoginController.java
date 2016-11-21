/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.domeinapp.MenuModel;
import com.boerpiet.viewapp.LoginView;
import com.boerpiet.viewapp.MenuView;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class LoginController {
    AccountModel accountModel;
    LoginView loginView;
    Scanner input = new Scanner(System.in);

    public LoginController(AccountModel accountModel, LoginView loginView) {
        this.accountModel = accountModel;
        this.loginView = loginView;
        login();
    }
    
    private void login() {
        loginView.showLogin();
        
        String login = listenForLogin();
        if(login.equalsIgnoreCase("exit") || login.equals("9"))
            return;

        if (accountModel.validateLogin(login)) {
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
        String usernamePassword = input.nextLine();
        if(usernamePassword.isEmpty())
            return listenForLogin();
        return usernamePassword;
    }
    
}
