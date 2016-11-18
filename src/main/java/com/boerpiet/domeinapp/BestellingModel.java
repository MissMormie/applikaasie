/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.sql.Date;

/**
 *
 * @author Peaq
 */
public class BestellingModel {
    private BestellingModel bm;
    private BestellingPojo bp;
    private BestelArtikelPojo ba;
    private ArtikelPojo ap;
    
    public BestellingModel () {
        
    }
        
    public BestellingModel (BestellingModel bm, BestellingPojo bp, BestelArtikelPojo ba, ArtikelPojo ap) {
        this.bm = bm;
        this.bp = bp;
        this.ba = ba; 
        this.ap = ap;
    }    
    
    public BestellingModel getBestellingModel () {
        return bm;
    }
    public void setBestellingModel (BestellingModel bm) {
        this.bm = bm;
    }
    public BestellingPojo getBestellingPojo () {
        return bp;
    }
    public void setBestellingPojo (BestellingPojo bp) {
        this.bp = bp;
    }
    public BestelArtikelPojo getBestelArtikelPojo () {
        return ba;
    }
    public void setBestelArtikelPojo (BestelArtikelPojo ba) {
        this.ba = ba;
    }
    public ArtikelPojo getArtikelPojo () {
        return ap;
    }
    public void setArtikelPojo (ArtikelPojo ap) {
        this.ap = ap;
    }
}
