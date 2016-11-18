/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.domeinapp.ArtikelPojo;
import com.boerpiet.viewapp.ArtikelView;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class ArtikelController {
    private final Scanner input = new Scanner (System.in);
    private final ArtikelPojo artikelPojo;
    private final ArtikelView artikelView;
    
    public ArtikelController (ArtikelPojo artikelPojo, ArtikelView artikelView) {
        this.artikelPojo = artikelPojo;
        this.artikelView = artikelView;
        }
    
    public void createArticle () {
        artikelView.startCreateArticle ();
        addArticleToDatabase ();
        
    }
    
    private void addArticleToDatabase () {
        String begin = input.nextLine();
        if (begin.equalsIgnoreCase("N")) {
            return;
        }
        
        System.out.println("Geef naam voor artikel:");
        String naam = input.nextLine();
        System.out.println("Geef prijs voor artikel:");
        Double prijs = Double.parseDouble(input.nextLine());
        System.out.println("Geef huidige voorraad van dit artikel:");
        int voorraad = Integer.parseInt(input.nextLine());
        
        artikelPojo.setNaam(naam);
        artikelPojo.setPrijs(prijs);
        artikelPojo.setVoorraad(voorraad);
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").createArtikel(artikelPojo)) {
            System.out.println("Artikel is toegevoegd aan de database.");
            artikelView.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            createArticle();
        }
    }
    
    public void modifyArticle () {
        artikelView.articleModifyOptions();
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1: modifyArticleNaam ();
                    modifyArticle ();
                break;
            case 2: modifyArticlePrijs ();
                    modifyArticle ();
                break;
            case 3: modifyArticleVoorraad ();
                    modifyArticle ();
                break;
            case 4:
                return;
            default: modifyArticle ();
                break;
        }
    }
    
    private void modifyArticleNaam () {
        artikelView.showAllArticles();
        System.out.println("Geef artikelid voor wijziging:");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Geef de nieuwe naam:");
        String naam = input.nextLine();
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelNaam(naam, id)) {
            System.out.println("Artikel is gewijzigd.");
            artikelView.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            modifyArticle ();
        }
    }
    
    private void modifyArticlePrijs () {
        artikelView.showAllArticles();
        System.out.println("Geef artikelid voor wijziging:");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Geef de nieuwe prijs:");
        double prijs = Double.parseDouble(input.nextLine());
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelPrijs(prijs, id)) {
            System.out.println("Artikel is gewijzigd.");
            artikelView.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            modifyArticle ();
        }
    }
    
    private void modifyArticleVoorraad () {
        artikelView.showAllArticles();
        System.out.println("Geef artikelid voor wijziging:");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Geef de nieuwe voorraad:");
        int voorraad = Integer.parseInt(input.nextLine());
        
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").updateArtikelVoorraad(voorraad, id)) {
            System.out.println("Artikel is gewijzigd.");
            artikelView.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            modifyArticle ();
        }
    }
    
    public void deleteArticle () {
        artikelView.startDeleteArticle();
        artikelView.showAllArticles();
        System.out.println("Geef id van artikel voor verwijdering:");
        int id = Integer.parseInt(input.nextLine());
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").deleteArtikel(id)) {
            System.out.println("Artikel is nu verwijderd.");
            artikelView.showAllArticles();
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            return;
        }
        System.out.println("Wil je nog meer artikelen verwijderen? (J/N)");
        String jaNee = input.nextLine();
        if (jaNee.equalsIgnoreCase("j")) {
            deleteArticle ();
        }
    }
}