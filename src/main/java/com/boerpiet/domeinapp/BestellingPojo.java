/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Peaq
 */
public class BestellingPojo {
    private int id;
    private int klantKey;
    private Date bestelDatum;
    private int accountKey;
    private boolean deleted;
    private boolean afgehandeld;
    
    public BestellingPojo () {};
    
    public BestellingPojo (int klantKey, Date bestelDatum, int accountKey) {
        this.klantKey = klantKey;
        this.bestelDatum = bestelDatum;
        this.accountKey = accountKey;
        }
    
    public BestellingPojo (int klantKey, Date bestelDatum, int accountKey, boolean deleted, boolean afgehandeld) {
        this.klantKey = klantKey;
        this.bestelDatum = bestelDatum;
        this.accountKey = accountKey;
        this.deleted = deleted;
        this.afgehandeld = afgehandeld;
    }
    
    public int getId (){
        return id;
    }
    public void setId (int id) {
        this.id = id;
    }
    public int getKlantKey() {
        return klantKey;
    }
    public void setKlantKey (int klantKey) {
        this.klantKey = klantKey;
    }
    public Date getBestelDatum () {
        return bestelDatum;
    }
    public void setBestelDatum (Date bestelDatum) {
        this.bestelDatum = bestelDatum;
    }
    public int getAccountKey () {
        return accountKey;
    }
    public void setAccountKey (int accountKey) {
        this.accountKey = accountKey;
    }
    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted (boolean deleted) {
        this.deleted = deleted;
    }
    public boolean isAfgehandeld () {
        return afgehandeld;
    }
    public void setAfgehandeld (boolean afgehandeld) {
        this.afgehandeld = afgehandeld;
    }
}