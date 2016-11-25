/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.cheeseapp.Bestelling.BestellingDaoFactory;
import com.boerpiet.viewapp.BestelArtikelView;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestelArtikelModel {
    
    private BestelArtikelView bav;
    private BestelArtikelModel bam;
    private BestelArtikelPojo bap;
    private final Scanner input = new Scanner (System.in);
    
    public BestelArtikelModel () {
        
    }
    
    public BestelArtikelModel (BestelArtikelModel bam) {
        this.bam = bam;
    }
    
    public boolean checkBestelRegelId (int inputBestelRegelId, int bestelId) {
        bap = new BestelArtikelPojo ();
        ArrayList <BestelArtikelPojo> aList = BestelArtikelDaoFactory.
                getBestelArtikelDAO("MySQL").getBestelLijstByBestelId(bestelId);
        for (BestelArtikelPojo bapLoop : aList) {
            if (inputBestelRegelId == idBestelRegelList (bapLoop)) {
                System.out.println("Bestelregelid gevonden");
                break;
            } return false;
        }
        return true;
    }
    
    private int idBestelRegelList (BestelArtikelPojo bap) {
        int id = bap.getId();
        return id;
    }
    
    //Getters and setters
    public BestelArtikelView getBav () {
        return bav;
    }
    public void setBav (BestelArtikelView bav) {
        this.bav = bav;
    }
    public BestelArtikelModel getBam () {
        return bam;
    }
    public void setBam (BestelArtikelModel bam) {
        this.bam = bam;
    }
}
