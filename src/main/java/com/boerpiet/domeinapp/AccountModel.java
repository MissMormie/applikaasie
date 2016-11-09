/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.util.Date;



/**
 *
 * @author Sonja
 */
public class AccountModel {
    private int idAccount;
    private String gebruikersnaam;
    private String wachtwoord; // moet deze hierin? Slaan we dit ooit middellange termijn op?
    private String accountStatus;
    private Date Datum_aanmaak;
    private int klantId;
    private boolean deleted;

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getDatum_aanmaak() {
        return Datum_aanmaak;
    }

    public void setDatum_aanmaak(Date Datum_aanmaak) {
        this.Datum_aanmaak = Datum_aanmaak;
    }

    public int getKlantId() {
        return klantId;
    }

    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
