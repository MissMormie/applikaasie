/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.cheeseapp.Bestelling.BestellingDaoFactory;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestelArtikelView;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Peaq
 */
public class BestellingModel {
    private final Scanner input = new Scanner (System.in);
    private BestellingModel bm;
    private BestellingView bv;
    private BestellingPojo bp;
    private BestelArtikelPojo ba;
    private ArtikelPojo ap;
    private ArtikelView av;
    private BestelArtikelView bav;
    
    public BestellingModel () {  
    }
    
    public BestellingModel (BestellingModel bm, BestellingView bv, BestellingPojo bp,
            BestelArtikelPojo ba, ArtikelPojo ap, ArtikelView av, BestelArtikelView bav) {
        this.bm = bm;
        this.bv = bv;
        this.bp = bp;
        this.ba = ba; 
        this.ap = ap;
        this.av = av;
        this.bav = bav;
    }
    //Methoden voor menu-opties
    public void addNewOrder (int klantId, Date sqlDatum, int accountKey, int artikelId, int aantal) {
        bp = new BestellingPojo ();
        bv = new BestellingView ();
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").findArtikelId(klantId)) {
        bp.setKlantKey (klantId);
        bp.setBestelDatum (sqlDatum);
        bp.setAccountKey (accountKey);
        
        int id = BestellingDaoFactory.getBestellingDAO("MySQL").createBestellingWithReturnId(bp);
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (id, artikelId, aantal);
        
        BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel);
        bv.showNewBestellingSucces();
        } else {
            bv.showNewBestellingFailure ();
        }
    }
    
    public void createArticleToAdd (int bestelId, int artikelId, int aantal) {
        bv = new BestellingView();
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (bestelId, artikelId, aantal);
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel)) {
            System.out.println("Artikel is toegevoegd aan bestelling met id: "+bestelId);
            
        }else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            return;
        }
        System.out.println("Dit is de gewijzigde bestelling.");
        bv.showAllBestelRegelsByBestelId(bestelId);
    }
    
    public void modifyArticleInOrder (int bestelId, int regelId, int artikelId, int aantal) {
        bv = new BestellingView();
        ba = new BestelArtikelPojo();
        ba.setBestelId(bestelId);
        ba.setArtikelId(artikelId);
        ba.setAantal(aantal);
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").updateBestelArtikel(ba, regelId)) {
            System.out.println("Bestelling is nu gewijzigd.");
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            return;
        }
        System.out.println("Dit is de gewijzigde bestelling.");
        bv.showAllBestelRegelsByBestelId(bestelId);
    }
    
    public void deleteOneTupel (int klantId, int brId, int bestelId) {
        bv = new BestellingView();
        ba = new BestelArtikelPojo ();
        ba.setId(brId);
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteBestelArtikel(brId)) {
            System.out.println("Artikel is verwijderd van bestelling.");
            System.out.println("Dit is de gewijzigde bestelling.");
            bv.showAllBestelRegelsByBestelId(bestelId);
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
    public void deleteOrder (int klantId, int bestelId) {
        bv = new BestellingView ();
        bp = new BestellingPojo ();
        bp.setId(bestelId);
        //bp.setKlantKey(klantId);
        
        deleteArticlesFromOrder(bestelId);
        
        if (BestellingDaoFactory.getBestellingDAO("MySQL").deleteBestelling(bestelId)) {
            System.out.println("Bestelling is verwijderd.");
            bv.showAllOrdersByKlantId(klantId);            
        }
    }
    
    private void deleteArticlesFromOrder (int bestelId) {
        ArrayList<BestelArtikelPojo>baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        for (BestelArtikelPojo baPojo : baList) {
            BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteArticleFromOrder(bestelId);
            }
    }
    
    //check bestellingid adhv klantid
    public boolean checkOrderId (int inputOrderId, int klantId) {
        bp = new BestellingPojo ();
        ArrayList <BestellingPojo> aList = BestellingDaoFactory.getBestellingDAO("MySQL").getAllByKlantId(klantId);
        for (BestellingPojo bp : aList) {
            if (inputOrderId == idOrderList (bp)) {
                System.out.println("Bestellingid gevonden");
                break;
            } return false;
        }
        return true;
    }
    
    private int idOrderList (BestellingPojo bp) {
        int id = bp.getId();
        return id;
    }
    
    //getters and setters
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