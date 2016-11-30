/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.bestelartikel.BestelArtikelDaoFactory;
import com.boerpiet.viewapp.BestelArtikelView;

/**
 *
 * @author Peaq
 */
public class BestelArtikelModel {
    
    private BestelArtikelView bav;
    private BestelArtikelModel bam;
    private BestelArtikelPojo bap;
    
    public BestelArtikelModel () {
        
    }
    
    public BestelArtikelModel (BestelArtikelModel bam) {
        this.bam = bam;
    }
    
    public boolean checkOAIdInDataBase (int inputOAId) {
        if (inputOAIdSmallerMaxId(inputOAId)) {
            return BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").findBestelArtikel(inputOAId);
            } else {
            return false;
        }
    }
    
    private boolean inputOAIdSmallerMaxId (int id) {
        return id<BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").getMaxBestelArtikelId();
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
