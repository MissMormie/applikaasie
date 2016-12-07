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
@Table (name = "adres_type")
public class AdresTypePojo implements Serializable {
    
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private int idAdres_type = 0;
    
    @Column (name = "soort")
    @Enumerated (EnumType.STRING)
    private AdresType Soort;

    public int getIdAdres_type() {
        return idAdres_type;
    }

    public AdresType getSoort() {
        return Soort;
    }
    
    // No setters because immutable class. 

    public void setSoort(AdresType Soort) {
        this.Soort = Soort;
    }
    
    // TODO Remove setters
    public void setIdAdres_type(int idAdres_type) {
        this.idAdres_type = idAdres_type;
    }

}
