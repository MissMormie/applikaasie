/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.artikel.ArtikelDaoFactory;
import com.boerpiet.dao.bestelartikel.BestelArtikelDaoFactory;
import com.boerpiet.dao.bestelling.BestellingDaoFactory;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestelArtikelView;
import java.sql.Date;
import java.util.ArrayList;


/**
 *
 * @author Peaq
 */
public class BestellingModel {
    private BestellingModel bm;
    private BestellingView bv;
    private BestellingPojo bp;
    private BestelArtikelPojo ba;
    private ArtikelPojo ap;
    private ArtikelView av;
    private BestelArtikelView bav;
    
    public BestellingModel () {  
    }
    
    public BestellingModel (BestellingModel bm, BestellingView bv, BestellingPojo bp,
            BestelArtikelPojo ba, ArtikelPojo ap, ArtikelView av, BestelArtikelView bav) {
        this.bm = bm;
        this.bv = bv;
        this.bp = bp;
        this.ba = ba; 
        this.ap = ap;
        this.av = av;
        this.bav = bav;
    }
    //Methoden voor menu-opties
    public void addNewOrder (int klantId, Date sqlDatum, int accountKey, int artikelId, int aantal) {
        bp = new BestellingPojo ();
        bv = new BestellingView ();
        
        if (ArtikelDaoFactory.getArtikelDAO().isArtikelInDatabase(artikelId)) {
            bp.setKlantKey (klantId);
            bp.setBestelDatum (sqlDatum);
            bp.setAccountKey (accountKey);
         
            int id = BestellingDaoFactory.getBestellingDAO("MySQL").createBestellingWithReturnId(bp);
            
            BestelArtikelPojo bestelregel = new BestelArtikelPojo (id, artikelId, aantal);
            
            BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel);
            
            bv.showAddOrderSuccess();
        } else {
            bv.showErrorMessage();
        }
    }
    
    public void createArticleToAddToOrder (int bestelId, int artikelId, int aantal) {
        bv = new BestellingView();
        bav = new BestelArtikelView ();
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (bestelId, artikelId, aantal);
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel)) {
            bav.showAddOASuccess();
            bv.showAllBestelRegelsByBestelId(bestelId);  
        }else {
            bv.showErrorMessage();
        }        
    }
    
    public void modifyArticleInOrder (int bestelId, int regelId, int artikelId, int aantal) {
        bv = new BestellingView();
        ba = new BestelArtikelPojo();
        
        ba.setBestelId(bestelId);
        ba.setArtikelId(artikelId);
        ba.setAantal(aantal);
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").updateBestelArtikel(ba, regelId)) {
            bv.showModifySuccess();
            bv.showAllBestelRegelsByBestelId(bestelId);  
        } else {
            bv.showErrorMessage();
        }        
    }
    
    public void deleteOA (int klantId, int brId, int bestelId) {
        bv = new BestellingView();
        bav = new BestelArtikelView ();
        ba = new BestelArtikelPojo ();
        ba.setId(brId);
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteBestelArtikel(brId)) {
            bav.showDeleteOASuccess();
            bv.showAllBestelRegelsByBestelId(bestelId);
        } else {
            bv.showErrorMessage();
        }
    }
    
    public void deleteOrder (int klantId, int bestelId) {
        bv = new BestellingView ();
        bp = new BestellingPojo ();
        //bp.setId(bestelId);
        
        deleteArticlesFromOrder(bestelId);
        
        if (BestellingDaoFactory.getBestellingDAO("MySQL").deleteBestelling(bestelId)) {
            bv.showDeleteOrderSuccess();
            bv.orderListByKlantId(klantId);            
        } else {
            bv.showErrorMessage();
        }
    }
    
    private void deleteArticlesFromOrder (int bestelId) {
        ArrayList<BestelArtikelPojo>baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        
        for (BestelArtikelPojo baPojo : baList) {
                BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteArticleFromOrder(bestelId);
            }
        }
    
    public boolean checkNotEmptyOAListByOrderId (int bestelId) {
        ArrayList<BestelArtikelPojo>baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        if (baList.isEmpty()) {
            bv.showNoOAIdByOrderId(bestelId);
            return false;
        }
        return true;
    }
    
    public boolean checkOAIdByOrderId (int bestelId, int OAId) {
        ArrayList<BestelArtikelPojo> baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId (bestelId);
        for (BestelArtikelPojo bp : baList) {
            if (bp.getId() == OAId) {
                return true;                
            }
        }
        return false;
    }
    
    public boolean checkNotEmptyOrderListByKlantId (int klantId) {
        ArrayList<BestellingPojo> bList = BestellingDaoFactory.getBestellingDAO("MySQL").
                getAllByKlantId(klantId);
        if (bList.isEmpty()) {
            bv.showEmptyOrderListByKlantId (klantId);
            return false;
        }
        return true;
    }
    
    public boolean checkOrderIdByKlantId (int klantId, int bestelId) {
        ArrayList<BestellingPojo> bList = BestellingDaoFactory.getBestellingDAO("MySQL").
                getAllByKlantId(klantId);
        for (BestellingPojo bp  : bList) {
            if (bp.getId() == bestelId) {
                return true;
            }
        }
        return false;
    }
    
    //check bestellingid adhv klantid
    public boolean checkOrderIdInDatabase (int inputOrderId, int klantId) {
        if (inputOrderIdSmallerMaxId (inputOrderId)) {
            return (BestellingDaoFactory.getBestellingDAO("MySQL").findBestellingId(inputOrderId, klantId));    
        } else {
            return false;
        }
    }
    
    private boolean inputOrderIdSmallerMaxId (int id) {
        return (id<=BestellingDaoFactory.getBestellingDAO("MySQL").getMaxBestellingId());
    }
    
    //getters and setters
    public BestellingModel getBestellingModel () {
        return bm;
    }
    public void setBestellingModel (BestellingModel bm) {
        this.bm = bm;
    }
    public BestellingPojo getBestellingPojo () {
        return bp;
    }
    public void setBestellingPojo (BestellingPojo bp) {
        this.bp = bp;
    }
    public BestelArtikelPojo getBestelArtikelPojo () {
        return ba;
    }
    public void setBestelArtikelPojo (BestelArtikelPojo ba) {
        this.ba = ba;
    }
    public ArtikelPojo getArtikelPojo () {
        return ap;
    }
    public void setArtikelPojo (ArtikelPojo ap) {
        this.ap = ap;
    }
}