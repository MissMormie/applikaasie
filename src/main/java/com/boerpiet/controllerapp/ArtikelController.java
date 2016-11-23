/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

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
                
        String naam = artikelModel.inputName();
        
        double prijs = artikelModel.inputPrijs();
        
        int voorraad = artikelModel.inputVoorraad();
        
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
        
        int id = artikelModel.inputArticleId();
        
        String naam = artikelModel.inputName();
        
        artikelModel.modifyNaam(id, naam);
    }
    
    private void modifyArticlePrijs () {
        
        int id = artikelModel.inputArticleId();

        double prijs = artikelModel.inputPrijs();
        
        artikelModel.modifyPrijs(id, prijs);
    }
    
    private void modifyArticleVoorraad () {
        
        int id = artikelModel.inputArticleId();
        
        int voorraad = artikelModel.inputVoorraad();
        
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
        int id = artikelModel.inputArticleId();
        
        artikelModel.deleteArticle(id);
    }
}