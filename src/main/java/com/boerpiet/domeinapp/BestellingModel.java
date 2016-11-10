/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.time.LocalDate;

/**
 *
 * @author Peaq
 */
public class BestellingModel {
    private int id;
    private int klantKey;
    private LocalDate bestelDatum;
    private int accountKey;
    private boolean deleted;
    
    public BestellingModel () {};
    public BestellingModel (int id, int klantKey, LocalDate bestelDatum, int accountKey, boolean deleted) {
        this.id = id;
        this.klantKey = klantKey;
        this.bestelDatum = bestelDatum;
        this.accountKey = accountKey;
        this.deleted = deleted;
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
    public LocalDate getBestelDatum () {
        return bestelDatum;
    }
    public void setBestelDatum (LocalDate bestelDatum) {
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
}