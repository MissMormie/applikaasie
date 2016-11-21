/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.AccountView;
import com.boerpiet.viewapp.KlantenView;
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
    
    public void newKlantAccount() {
        KlantenController kc = new KlantenController(new KlantenModel(), new KlantenView());
        int id = kc.selectKlant().getKlantPojo().getId();
        newAccount(id);
    }
    
    public void newMedewerkerAccount() {
        newAccount(0);
    }
    
    private void newAccount(int id) {
        accountView.showNewAccount();
        newAccountListener(id);
    }
    
    // TODO write comments
    private void newAccountListener(int klantId) {
        String usernamePasswordType = "";
        usernamePasswordType = input.nextLine();

        // Check if input is N, if so, stop with new account
        if(usernamePasswordType.compareToIgnoreCase("n") == 0) 
            return;

        // Controleer of er minimaal 3 strings zijn
        String[] parts = usernamePasswordType.split(" ");
        if (parts.length < 2) {
            accountView.showWrongInput();
            newAccount(klantId);
            return;
        }

        // Make new account met parts[0] username, parts[1] wachtwoord, parts[2] klantID
        if(accountModel.createAccount(parts[0], parts[1], klantId)) {
            accountView.showNewAccountSuccess();
        } else {
            accountView.showNewAccountFailed();
            newAccount(klantId);
        }
    }

    // TODO finish selectAccountToModify
    public void selectAccountToModify() {
        accountView.showAccountList(accountModel.fetchAccountList());
        accountView.showSelectAccountToModify();
        selectAccountToModifyListener();
    }

    private void selectAccountToModifyListener() {
        String in = input.nextLine();

        // Check if back to menu.
        if (in.equalsIgnoreCase("n")) 
            return;
        
        // Check if valid input.
        if (!Validator.isValidInt(in)) {
            selectAccountToModify();        
        } else {
            int id = Integer.parseInt(in);
            AccountPojo account = accountModel.getAccountById(id);
            
            // If no account exists with that id
            if (account == null)
                selectAccountToModify();
            else 
                modifyAccount(account);
        }
    }

    public void deleteAccount() {
        accountView.showAccountList(accountModel.fetchAccountList());
        accountView.showDeleteAccount();
        deleteAccountListener();
    }

    private void deleteAccountListener() {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        // Check if valid input.
        if (!Validator.isValidInt(in)) {
            deleteAccount();
        } else {         
            int id = Integer.parseInt(in);
            if (accountModel.deleteAccountById(id)) {
                accountView.showDeleteAccountSuccess();
            } else {
                accountView.showDeleteAccountFail();
                deleteAccount();
            }
        }
    }

    private void modifyAccount(AccountPojo account) {
        accountView.showModifyAccount(account);
        modifyAccountListener(account);
    }
    
    public void modifyOwnPassword() {
        accountView.showModifyOwnPassword();
        modifyPasswordListener(accountModel.getLogin().getAccountPojo());
    }
    
    public void modifyOwnUsername() {
        AccountPojo account = accountModel.getLogin().getAccountPojo();
        accountView.showModifyOwnUsername();
        modifyUsernameListener(account);
    }

    // Listen for account update options 
    // based on choice select correct modify function.
    private void modifyAccountListener(AccountPojo account) {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        // Check if valid input.
        if (!Validator.isValidInt(in)) {
            modifyAccount(account);
        } else  {
            int id = Integer.parseInt(in);
        
            switch(id) {
                case 1: 
                    accountView.showModifyUsername(account);
                    modifyUsernameListener(account);
                    break;
                case 2: 
                    accountView.showModifyPassword(account);
                    modifyPasswordListener(account);
                    break;
                case 3: 
                    accountView.showModifyAccountStatus(account);
                    modifyAccountStatusListener(account);
                    break;
                case 4: 
                    accountView.showModifyKlantId(account);
                    modifyKlantIdListener(account);
                    break;
                default:
                    modifyAccount(account);
            }
        }
    }

    // Listen for new Username and pass this to model for update
    // Show success or fail message, on fail ask for new Username.
    private void modifyUsernameListener(AccountPojo account) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (accountModel.updateUsername(account, in)) { 
            accountView.showUpdateSuccess();
        } else {
            accountView.showUpdateFailed();
            accountView.showModifyUsername(account);
            modifyUsernameListener(account);
        }
    }

    // Listen for new Accountstatus and pass this to model for update
    // Show success or fail message, on fail ask for new Accountstatus.
    private void modifyPasswordListener(AccountPojo account) {
        String in = input.nextLine();
        
        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update password successful
        if (accountModel.updatePassword(account, in)) { 
            accountView.showUpdateSuccess();
        } else {
            accountView.showUpdateFailed();
            accountView.showModifyPassword(account);
            modifyPasswordListener(account);
        }
    }

    // Listen for new Accountstatus and pass this to model for update
    // Show success or fail message, on fail ask for new Accountstatus.
    private void modifyAccountStatusListener(AccountPojo account) {
        String in = input.nextLine();
        
        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update password successful
        if (accountModel.updateAccountStatus(account, in)) { 
            accountView.showUpdateSuccess();
        } else {
            accountView.showUpdateFailed();
            accountView.showModifyAccountStatus(account);
            modifyAccountStatusListener(account);
        }
    }

    // Listen for new KlantId and pass this to model for update
    // Show success or fail message, on fail ask for new klantId.
    private void modifyKlantIdListener(AccountPojo account) {
        String in = input.nextLine();
        
        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
                // Check if valid input.
        if (!Validator.isValidInt(in)) {
            accountView.showModifyKlantId(account);
            modifyKlantIdListener(account);
        } else  {
            int id = Integer.parseInt(in);

            //  Check if update password successful
            if (accountModel.updateAccountKlantId(account, id)) { 
                accountView.showUpdateSuccess();
            } else {
                accountView.showUpdateFailed();
                accountView.showModifyKlantId(account);
                modifyKlantIdListener(account);
            }
        }
    }
   
    
}
