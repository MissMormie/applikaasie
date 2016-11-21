/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
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
    
    public ArtikelModel (ArtikelModel am, ArtikelView av, ArtikelPojo ap) {
        this.am = am;
        this.av = av;
        this.ap = ap;
    }
    
    public void addArticle (String naam, double prijs, int voorraad) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setNaam(naam);
        ap.setPrijs(prijs);
        ap.setVoorraad(voorraad);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").createArtikel(ap)) {
            System.out.println("Artikel is toegevoegd aan de database.");
            av.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
    public void modifyNaam (int id, String naam) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setNaam(naam);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelNaam(naam, id)) {
            System.out.println("Artikel is gewijzigd.");
            av.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
    public void modifyPrijs (int id, double prijs) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setPrijs(prijs);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelPrijs(prijs, id)) {
            System.out.println("Artikel is gewijzigd.");
            av.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
    public void modifyVoorraad (int id, int voorraad) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        ap.setVoorraad(voorraad);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelVoorraad(voorraad, id)) {
            System.out.println("Artikel is gewijzigd.");
            av.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
    public void deleteArticle (int id) {
        av = new ArtikelView ();
        ap = new ArtikelPojo ();
        ap.setId(id);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").deleteArtikel(id)) {
            System.out.println("Artikel is nu verwijderd.");
            av.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
        }
    }
    
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