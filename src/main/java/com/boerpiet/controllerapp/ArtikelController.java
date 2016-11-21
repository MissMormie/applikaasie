/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.ArtikelPojo;
import com.boerpiet.viewapp.ArtikelView;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class ArtikelController {
    private final Scanner input = new Scanner (System.in);
    private final ArtikelModel artikelModel;
    private final ArtikelPojo artikelPojo;
    private final ArtikelView artikelView;
    
    public ArtikelController (ArtikelModel artikelModel, ArtikelPojo artikelPojo,
            ArtikelView artikelView) {
        this.artikelModel = artikelModel;
        this.artikelPojo = artikelPojo;
        this.artikelView = artikelView;
        }
    
    public void createArticle () {
        artikelView.startCreateArticle ();
        int keuze  = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1: addArticleToDatabase ();
                    createArticle ();
                break;
            case 2:
                return;
            default: addArticleToDatabase ();
                break;
        }        
    }
    
    private void addArticleToDatabase () {
                
        System.out.println("Geef naam voor artikel:");
        String naam = input.nextLine();
        
        System.out.println("Geef prijs voor artikel:");
        double prijs = Double.parseDouble(input.nextLine());
        
        System.out.println("Geef huidige voorraad van dit artikel:");
        int voorraad = Integer.parseInt(input.nextLine());
        
        artikelModel.addArticle(naam, prijs, voorraad);
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
        
        artikelModel.modifyNaam(id, naam);
    }
    
    private void modifyArticlePrijs () {
        artikelView.showAllArticles();
        System.out.println("Geef artikelid voor wijziging:");
        int id = Integer.parseInt(input.nextLine());
        
        System.out.println("Geef de nieuwe prijs:");
        double prijs = Double.parseDouble(input.nextLine());
        
        artikelModel.modifyPrijs(id, prijs);
    }
    
    private void modifyArticleVoorraad () {
        artikelView.showAllArticles();
        System.out.println("Geef artikelid voor wijziging:");
        int id = Integer.parseInt(input.nextLine());
        
        System.out.println("Geef de nieuwe voorraad:");
        int voorraad = Integer.parseInt(input.nextLine());
        
        artikelModel.modifyVoorraad(id, voorraad);
    }
    
    public void deleteArticleMenu () {
        artikelView.startDeleteArticle();
        
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1: deleteArticleFromDatabase ();
                    deleteArticleMenu ();
                break;
            case 2:
                return;
            default: deleteArticleMenu ();
                break;
        }
    }
    
    private void deleteArticleFromDatabase () {
        artikelView.showAllArticles();
        System.out.println("Geef id van artikel voor verwijdering:");
        int id = Integer.parseInt(input.nextLine());
        
        artikelModel.deleteArticle(id);
    }
}