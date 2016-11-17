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
public class KlantPojo {
    
    // ------------ VARIABLES ---------------------------------
    private int id = 0;
    private String voornaam;
    private String achternaam;
    private String tussenvoegsel;
    private String telefoonnummer;
    private String emailadres;
    private boolean deleted = false;

    // ------------ CONSTRUCTORS ---------------------------------
    
    /**
     * Initates empty KlantPojo
     */
    public KlantPojo() { }
    
    /**
     * Initiates KlantPojo with:
     * 
     * @param voornaam String
     * @param achternaam String
     * @param tussenvoegsel String
     * @param telefoonnummer String
     * @param emailadres String
     * @param deleted Boolean
     */
    public KlantPojo(String voornaam, String achternaam, String tussenvoegsel, String telefoonnummer, String emailadres, boolean deleted) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.tussenvoegsel = tussenvoegsel;
        this.telefoonnummer = telefoonnummer;
        this.emailadres = emailadres;
        this.deleted = deleted;
    }    

    // ------------ Getters and Setters ---------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "KlantPojo{" + "id=" + id + ", voornaam=" + voornaam + ", achternaam=" + achternaam + ", tussenvoegsel=" + tussenvoegsel + ", telefoonnummer=" + telefoonnummer + ", emailadres=" + emailadres + ", deleted=" + deleted + '}';
    }
    
}
