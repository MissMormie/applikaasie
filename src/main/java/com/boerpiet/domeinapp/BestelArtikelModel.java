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
public class BestelArtikelModel {
    private int id;
    private int aantal;
    private int bestelId;
    private int artikelId;
    private boolean deleted;
    
    public BestelArtikelModel (){};
    public BestelArtikelModel (int id, int aantal, int bestelId, int artikelId, boolean deleted) {
        this.id = id;
        this.aantal = aantal;
        this.bestelId = bestelId;
        this.artikelId = artikelId;
        this.deleted = deleted;
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
}