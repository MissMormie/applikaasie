/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.domeinapp.ArtikelPojo;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Peaq
 */
public class ArtikelView {
    public void showArtikelList (ArrayList<ArtikelPojo> artikelList) {
        showDivider();
        showArtikelListHeader();
        for (ArtikelPojo ap : artikelList) {
            showArtikelListItem(ap); 
        }
        System.out.println();
    }

    private void showArtikelListHeader() {
        System.out.printf("%-3s %-15s %-10s %s \n",
                "id",
                "naam",
                "prijs",
                "voorraad");
    }
    
    private void showArtikelListItem(ArtikelPojo ap) {
        System.out.printf("%-3s %-15s %-10s %s \n", 
                ap.getId(), 
                ap.getNaam(), 
                ap.getPrijs(),
                ap.getVoorraad());       
    }
    
    public void showDivider() {
        System.out.println("\n-----------------------------------------------");        
    }
    public void showAllArticles () {
        ArrayList <ArtikelPojo> aList = ArtikelDaoFactory.getArtikelDAO("MySQL").getAllArticles();
        ArtikelView artList = new ArtikelView();
        artList.showArtikelList(aList);
    }
}