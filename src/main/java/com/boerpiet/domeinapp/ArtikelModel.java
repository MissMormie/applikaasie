/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.artikel.ArtikelDaoFactory;
import com.boerpiet.viewapp.ArtikelView;

/**
 *
 * @author Peaq
 */
public class ArtikelModel {
    private ArtikelModel am;
    private ArtikelView av;
    private ArtikelPojo ap;
    
    public ArtikelModel () {
    }
    
    public ArtikelModel (ArtikelModel am) {
        this.am = am;
    }
    
    public void addArticle (String naam, double prijs, int voorraad) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setNaam(naam);
        ap.setPrijs(prijs);
        ap.setVoorraad(voorraad);
        
        int id = ArtikelDaoFactory.getArtikelDAO("MySQL").createArtikelWithReturnId(ap);
        
        if (id>0) {
            av.showArticle(id);
            av.showAddSuccess();
        } else {
            av.showErrorMessage();
        }
    }
    
    public void modifyNaam (int id, String naam) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setNaam(naam);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelNaam(naam, id)) {
            av.showModifySuccess();
            av.showArticle(id);
        } else {
            av.showErrorMessage();
        }
    }
    
    public void modifyPrijs (int id, double prijs) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setPrijs(prijs);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelPrijs(prijs, id)) {
            av.showModifySuccess();
            av.showArticle (id);
        } else {
            av.showErrorMessage();
        }
    }
    
    public void modifyVoorraad (int id, int voorraad) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setVoorraad(voorraad);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelVoorraad(voorraad, id)) {
            av.showModifySuccess();
            av.showArticle (id);
        } else {
            av.showErrorMessage();
        }
    }
    
    public void modifyNPV(int id, String naam, double prijs, int voorraad) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        
        ap.setId(id);
        ap.setNaam(naam);
        ap.setPrijs(prijs);
        ap.setVoorraad(voorraad);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelAll(ap)) {
            av.showModifySuccess();
            av.showArticle (id);
        } else {
            av.showErrorMessage();
        }
    }
    
    public void deleteArticle (int id) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").deleteArtikel(id)) {
            av.showDeleteSuccess();
        } else {
            av.showErrorMessage();
        }
    }
    
    public boolean checkArticleIdInDatabase (int inputArtikelId) {
        if (inputArtikelIdSmallerMaxId (inputArtikelId)) {
            return (ArtikelDaoFactory.getArtikelDAO("MySQL").findArtikelId(inputArtikelId));
            } else {
            return false;
        }
    }
    
    private boolean inputArtikelIdSmallerMaxId (int id) {
        return (id<=ArtikelDaoFactory.getArtikelDAO("MySQL").getMaxArtikelId());
    }

    //Getters and setters
    public ArtikelModel getArtikelModel () {
        return am;
    }
    public void setArtikelModel (ArtikelModel am) {
        this.am = am;
    }
    public ArtikelView getArtikelView () {
        return av;
    }
    public void setArtikelView (ArtikelView av) {
        this.av = av;
    }
    public ArtikelPojo getArtikelPojo () {
        return ap;
    }
    public void setArtikelPojo (ArtikelPojo ap) {
        this.ap = ap;
    }
}