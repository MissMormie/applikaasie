/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonja
 */
public class AdresPojo implements Cloneable {
    private int idAdres;
    private String straat;
    private int huisnummer;
    private String toevoeging;
    private String woonplaats;
    private boolean deleted;
    private String adresType; // Deze staat niet direct in deze database tabel maar hoort hier wel bij. Eigenlijk zou adres_type tabel bij adres moeten en niet bij klant heeft adres omdat het iets zegt over het type adres niet over de relatie met klant. 


    public AdresPojo(int idAdres, String straat, int huisnummer, String toevoeging, String woonplaats, boolean deleted, String adresType) {
        this.idAdres = idAdres;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.toevoeging = toevoeging;
        this.woonplaats = woonplaats;
        this.deleted = deleted;
        this.adresType = adresType;
    }

    public AdresPojo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdAdres() {
        return idAdres;
    }

    public void setIdAdres(int idAdres) {
        this.idAdres = idAdres;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public int getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }   
    
    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }
    
    @Override
    public AdresPojo clone() {
        try {
            AdresPojo clone = (AdresPojo) super.clone();
            return clone;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AdresPojo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}