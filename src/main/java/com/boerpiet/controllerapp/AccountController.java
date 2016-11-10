/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.viewapp.AccountView;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class AccountController {  
    private final AccountModel accountModel;
    private final AccountView accountView;
    private final Scanner input = new Scanner(System.in);
    
    public AccountController(AccountModel accountModel, AccountView accountView) {
        this.accountModel = accountModel;
        this.accountView = accountView;
    }
    
    public void newAccount() {
        accountView.showNewAccount();
        newAccountListener();
    }
    
    
    private void newAccountListener() {
        String usernamePasswordType = "";
        try {
            usernamePasswordType = input.nextLine();
            
            // Check if input is N, if so, stop with new account
            if(usernamePasswordType.compareToIgnoreCase("N") == 0) 
                return;

            // Dit deel van functie over zetten naar methode validate input?
            String[] parts = usernamePasswordType.split(" ");
            if (parts.length < 3) {
                accountView.showWrongInput();
                newAccount();
                return;
            }
            
            if(accountModel.createAccount(parts[0], parts[1], parts[2])) {
                accountView.showNewAccountSuccess();
            } else {
                accountView.showNewAccountFailed();
                newAccount();
            }   
        } catch (Exception ex) {
            newAccount();
        }
    }

    // TODO finish changeAccount
    public void changeAccount() {
        accountView.showAccountList(accountModel.fetchAccountList());
    }

    public void deleteAccount() {
        accountView.showAccountList(accountModel.fetchAccountList());
        accountView.showDeleteAccount();
        deleteAccountListener();
    }

    
    // Why doesn't it wait for NextLine? 
    private void deleteAccountListener() {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
        int id = Integer.parseInt(in);
        if (accountModel.deleteAccountById(id)) {
            accountView.showDeleteAccountSuccess();
        } else {
            accountView.showDeleteAccountFail();
            deleteAccount();
        }
    }
   
    
}
