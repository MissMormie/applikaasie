/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

/**
 *
 * @author Sonja
 */
public class Klant_heeft_AdresPojo {
    private int idKlant_heeft_adres;
    private int klantId;
    private int adresId;
    private int adres_typeID;
    private String adresType;

    public Klant_heeft_AdresPojo(int idKlant_heeft_adres, int klantId, int adresId, int adres_typeID, String adresType) {
        this.idKlant_heeft_adres = idKlant_heeft_adres;
        this.klantId = klantId;
        this.adresId = adresId;
        this.adres_typeID = adres_typeID;
        this.adresType = adresType;
    }

    public int getIdKlant_heeft_adres() {
        return idKlant_heeft_adres;
    }

    public void setIdKlant_heeft_adres(int idKlant_heeft_adres) {
        this.idKlant_heeft_adres = idKlant_heeft_adres;
    }

    public int getKlantId() {
        return klantId;
    }

    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    public int getAdresId() {
        return adresId;
    }

    public void setAdresId(int adresId) {
        this.adresId = adresId;
    }

    public int getAdres_typeID() {
        return adres_typeID;
    }

    public void setAdres_typeID(int adres_typeID) {
        this.adres_typeID = adres_typeID;
    }

    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }
    
}
