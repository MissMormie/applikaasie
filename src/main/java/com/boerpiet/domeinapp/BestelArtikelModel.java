/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.viewapp.BestelArtikelView;
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
    
    public boolean checkBestelRegelId (int inputBaId) {
        return BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").findBestelArtikel(inputBaId);
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
    public BestelArtikelPojo getBap () {
        return bap;
    }
    public void setBap (BestelArtikelPojo bap) {
        this.bap = bap;
    }
}
