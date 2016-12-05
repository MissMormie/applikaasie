/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.io.Serializable;
import javax.persistence.*;
/**
 *
 * @author Sonja
 */
@Entity
@Table (name = "klant_heeft_adres")
public class KlantHeeftAdresPojo implements Serializable {
    
    // ------------ VARIABLES ---------------------------------
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private int idKlant_heeft_adres;
    
    @ManyToOne
    @JoinColumn (name = "KlantId")
    private KlantPojo klant;
    
    @OneToOne
    @JoinColumn (name = "AdresId")
    private AdresPojo adres;
    
    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "Adres_typeId")
    private AdresTypePojo adres_type;

    @Column (name = "Deleted")
    private boolean deleted = false;
    
    // ------------ CONSTRUCTORS ---------------------------------
    
    public KlantHeeftAdresPojo() {
        
    }

    // ------------ Getters and Setters ---------------------------------

    public int getIdKlant_heeft_adres() {
        return idKlant_heeft_adres;
    }

    public void setIdKlant_heeft_adres(int idKlant_heeft_adres) {
        this.idKlant_heeft_adres = idKlant_heeft_adres;
    }

    public KlantPojo getKlant() {
        return klant;
    }

    public void setKlant(KlantPojo klant) {
        this.klant = klant;
    }

    public AdresPojo getAdres() {
        return adres;
    }

    public void setAdres(AdresPojo adres) {
        this.adres = adres;
    }

    public AdresTypePojo getAdres_type() {
        return adres_type;
    }

    public void setAdres_type(AdresTypePojo adres_type) {
        this.adres_type = adres_type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
