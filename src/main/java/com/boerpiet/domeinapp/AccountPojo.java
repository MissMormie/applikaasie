/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.GregorianCalendar ;


/**
 *
 * @author Sonja
 */
public class AccountPojo {
    private int idAccount;
    private String gebruikersnaam;
    private String wachtwoordHash; // moet deze hierin? Slaan we dit ooit middellange termijn op?
    private String accountStatus = "customer";
    private GregorianCalendar datum_aanmaak = new GregorianCalendar();
    private int klantId = 0;
    private boolean deleted = false;

    
    // -------------- Constructor ----------------

    public AccountPojo(int idAccount, String gebruikersnaam, String wachtwoordHash, String accountStatus, GregorianCalendar datum_aanmaak, int klantId, boolean deleted) {
        this.idAccount = idAccount;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoordHash = wachtwoordHash;
        this.accountStatus = accountStatus;
        this.datum_aanmaak = datum_aanmaak;
        this.klantId = klantId;
        this.deleted = deleted;
    }

    public AccountPojo(String gebruikersnaam, String plainTextWachtwoord) {
        this.gebruikersnaam = gebruikersnaam;
        this.setWachtwoordPlainText(plainTextWachtwoord);
    }
    
    public AccountPojo() {
        
    }
    // -------------- Getters and Setters ----------------
    
    public void setWachtwoordHash(String wachtwoord) {
        this.wachtwoordHash = wachtwoord;
    }
    
    public String getWachtwoordHash() {
        return wachtwoordHash;
    }

    public void setWachtwoordPlainText(String wachtwoord) {
        this.wachtwoordHash = makeWachtwoordHash(wachtwoord);
    }
    
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
    
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    //TODO
    public GregorianCalendar getDatum_aanmaak() {
        return datum_aanmaak;
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
    
    
    // ------------------ Public functions -------------------------
    
    // ------------------ Private functions -------------------------
    /** 
     * Hash function from: http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
     * 
     * @param base
     * @return 
     */
    private String makeWachtwoordHash(String base) {
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

    @Override
    public String toString() {
        return "AccountPojo{" + "idAccount=" + idAccount + ", gebruikersnaam=" + gebruikersnaam + ", wachtwoordHash=" + wachtwoordHash + ", accountStatus=" + accountStatus + ", datum_aanmaak=" + datum_aanmaak + ", klantId=" + klantId + ", deleted=" + deleted + '}';
    }
    
}

