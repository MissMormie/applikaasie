/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.GregorianCalendar ;


/**
 *
 * @author Sonja
 */
public class AccountModel {
    private int idAccount;
    private String gebruikersnaam;
    private String wachtwoord; // moet deze hierin? Slaan we dit ooit middellange termijn op?
    private String accountStatus = "customer";
    private GregorianCalendar datum_aanmaak = new GregorianCalendar();
    private int klantId = 0;
    private boolean deleted = false;

    
    // -------------- Constructor ----------------

    public AccountModel(String gebruikersnaam, String wachtwoord, String accountStatus) {
        this.gebruikersnaam = gebruikersnaam;
        this.setWachtwoord(wachtwoord);
        this.accountStatus = accountStatus;
    }
    
    // -------------- Getters and Setters ----------------

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

    //TODO
    public String getDatum_aanmaak() {
        return "2016-11-09";
    }

    public void setDatum_aanmaak(GregorianCalendar Datum_aanmaak) {
        this.datum_aanmaak = Datum_aanmaak;
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
    
    public String getHashWachtwoord() {
        return hashWachtwoord(wachtwoord);
    }
    
    // ------------------ Public functions -------------------------
    
    // ------------------ Private functions -------------------------
    /** 
     * Hash function from: http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
     * 
     * @param base
     * @return 
     */
    private String hashWachtwoord(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException ex){
           throw new RuntimeException(ex);
        }
    }    
    
}

