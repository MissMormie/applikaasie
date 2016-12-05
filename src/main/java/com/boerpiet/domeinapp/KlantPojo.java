/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Sonja
 */
@Entity
@Table(name = "klant")
public class KlantPojo implements Serializable {
    
    // ------------ VARIABLES ---------------------------------
    
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private int idKlant = 0;
    
    @Column (name = "Voornaam") 
    private String voornaam;
    
    @Column (name = "Achternaam")
    private String achternaam;
    
    @Column (name = "Tussenvoegsel")
    private String tussenvoegsel;
    
    @Column (name = "Telefoonnummer")
    private String telefoonnummer;
    
    @Column (name = "Emailadres")
    private String emailadres;
    
    @Column (name = "Deleted")
    private boolean deleted = false;
    
    // TODO change fetch type to lazy, because most of the time we dont' need all info. 
    // http://stackoverflow.com/a/22645558/7172179
    
    // mapped by  references the variable name in the KlantHeeftAdresPojo class that it's mapped to. 
    // Beware, this won't change when using refactor.
    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "klant")
    private Set<KlantHeeftAdresPojo> adressen = new HashSet<>(0);
    
    // ------------ CONSTRUCTORS ---------------------------------
    
    /**
     * Initiates empty KlantPojo
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

    public int getIdKlant() {
        return idKlant;
    }

    public void setIdKlant(int idKlant) {
        this.idKlant = idKlant;
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

    public Set<KlantHeeftAdresPojo> getAdressen() {
        return adressen;
    }

    public void setAdressen(Set<KlantHeeftAdresPojo> adressen) {
        this.adressen = adressen;
    }

    @Override
    public String toString() {
        return "KlantPojo{" + "id=" + idKlant + ", voornaam=" + voornaam + ", achternaam=" + achternaam + ", tussenvoegsel=" + tussenvoegsel + ", telefoonnummer=" + telefoonnummer + ", emailadres=" + emailadres + ", deleted=" + deleted + '}';
    }
    
}
