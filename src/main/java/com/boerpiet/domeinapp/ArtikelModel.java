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
public class ArtikelModel {
    private int id;
    private String naam;
    private double prijs;
    private int voorraad;
    private boolean deleted;
    
    public ArtikelModel (){};
    public ArtikelModel (int id, String naam, double prijs, int voorraad, boolean deleted) {
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
        this.voorraad = voorraad;
        this.deleted = deleted;
    }
    
    public int getId () {
        return id;
    }
    public void setId (int id) {
        this.id = id;
    }
    public String getNaam () {
        return naam;
    }
    public void setNaam (String naam) {
        this.naam = naam;
    }
    public double getPrijs () {
        return prijs;
    }
    public void setPrijs (double prijs) {
        this.prijs = prijs;
    }
    public int getVoorraad () {
        return voorraad;
    }
    public void setVoorraad (int voorraad) {
        this.voorraad = voorraad;
    }
    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted (boolean deleted) {
        this.deleted = deleted;
    }
}