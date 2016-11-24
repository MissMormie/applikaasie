/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.ArtikelPojo;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.ArtikelView;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class ArtikelController {
    private final Scanner input = new Scanner (System.in);
    private ArtikelModel am;
    private ArtikelPojo ap;
    private ArtikelView av;
    
    public ArtikelController (ArtikelModel am) {
        this.am = am;
        }

    public ArtikelController() {
        
    }
    
    public void createArticle () {
        av = new ArtikelView ();
        
        av.startCreateArticle ();
        int keuze  = inputIntCheck();
        
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
                
        String naam = inputName();
        
        double prijs = inputPrijs();
        
        int voorraad = inputVoorraad();
        
        am.addArticle(naam, prijs, voorraad);
    }
    
    public void modifyArticle () {
        av = new ArtikelView ();
        
        av.articleModifyOptions();
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
        
        int id = inputArticleId();
        
        String naam = inputName();
        
        am.modifyNaam(id, naam);
    }
    
    private void modifyArticlePrijs () {
        
        int id = inputArticleId();

        double prijs = inputPrijs();
        
        am.modifyPrijs(id, prijs);
    }
    
    private void modifyArticleVoorraad () {
        
        int id = inputArticleId();
        
        int voorraad = inputVoorraad();
        
        am.modifyVoorraad(id, voorraad);
    }
    
    public void deleteArticleMenu () {
        av = new ArtikelView ();
        
        av.startDeleteArticle();
        
        int keuze = inputIntCheck ();
        
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
        int id = inputArticleId();
        
        am.deleteArticle(id);
    }
    
    //input methods
    public int inputArticleId () {
        av = new ArtikelView ();
        av.showAllArticles();
        av.showInputArticleId();
        int artikelId = inputIntCheck();
        return artikelId;
    }
    
    public String inputName () {
        av = new ArtikelView ();
        av.showInputName();
        String naam = input.nextLine();
        return naam;
    }
    
    public double inputPrijs () {
        av = new ArtikelView ();
        av.showInputPrijs();
        double prijs = inputDoubleCheck();
        return prijs;
    }
    
    public int inputVoorraad () {
        av = new ArtikelView ();
        av.showInputVoorraad();
        int voorraad = inputIntCheck();
        return voorraad;
    }
    
    //validate input methods
    private int inputIntCheck () {
        av = new ArtikelView ();
        
        String intInput = input.nextLine();
        if (Validator.isValidInt(intInput)) {
            return Integer.parseInt(intInput);
        } else {
            av.showGiveNumber ();
            return inputIntCheck ();
        }
    }
    
    private double inputDoubleCheck () {
        av = new ArtikelView ();
        
        String doubleInput = input.nextLine();
        if (Validator.isValidDouble(doubleInput)) {
            return Double.parseDouble(doubleInput);
        } else {
            av.showGivePrijs ();
            return inputDoubleCheck ();
        }
    }
}