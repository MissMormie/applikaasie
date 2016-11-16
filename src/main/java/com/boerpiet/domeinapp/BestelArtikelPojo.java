/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

/**
 *
 * @author Peaq
 */
public class BestelArtikelPojo {
    private int id;
    private int aantal;
    private int bestelId;
    private int artikelId;
    private boolean deleted;
    private boolean bezorgd;
    
    public BestelArtikelPojo (){};
    
    public BestelArtikelPojo (int bestelId, int artikelId, int aantal) {
        this.bestelId = bestelId;
        this.artikelId = artikelId;
        this.aantal = aantal;
    }
    
    public BestelArtikelPojo (int id, int bestelId, int artikelId, int aantal, boolean deleted, boolean bezorgd) {
        this.id = id;
        this.bestelId = bestelId;
        this.artikelId = artikelId;
        this.aantal = aantal;
        this.deleted = deleted;
        this.bezorgd = bezorgd;
    }
    
    public int getId () {
        return id;
    }
    public void setId (int id) {
        this.id = id;
    }
    public int getAantal () {
        return aantal;
    }
    public void setAantal (int aantal) {
        this.aantal = aantal;
    }
    public int getBestelId () {
        return bestelId;
    }
    public void setBestelId (int bestelId) {
        this.bestelId = bestelId;
    }
    public int getArtikelId () {
        return artikelId;
    }
    public void setArtikelId (int artikelId) {
        this.artikelId = artikelId;
    }
    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted (boolean deleted) {
        this.deleted = deleted;
    }
    public boolean isBezorgd () {
        return bezorgd;
    }
    public void setBezorgd (boolean bezorgd) {
        this.bezorgd = bezorgd;
    }
}