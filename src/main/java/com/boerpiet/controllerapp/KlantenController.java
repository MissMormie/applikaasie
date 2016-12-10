/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.utility.ConsoleInput;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.KlantenView;
import com.boerpiet.viewapp.KlantView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class KlantenController {
    private final KlantenModel klantModel;
    private final KlantenView klantView;
    
    public KlantenController(KlantenModel klantModel, KlantenView klantView) {
        this.klantModel = klantModel;
        this.klantView = klantView;
    }

    public void selectKlantToModify() {
        List klantList = klantModel.fetchKlantList();
        klantView.showKlantList(klantList);
        klantView.showSelectKlantToModify();
        KlantModel klant = selectKlantListener(klantList);
        if(klant == null)
            return;

        KlantController skc = new KlantController(klant, new KlantView());
        skc.modifyKlant();        

    }

    public void selectKlantToDelete() {
        List klantList = klantModel.fetchKlantList();
        klantView.showKlantList(klantList);
        klantView.showSelectKlantToDelete();
        KlantModel klant = selectKlantListener(klantList);
        if(klant == null)
            return;
        KlantController skc = new KlantController(klant, new KlantView());
        skc.deleteKlant();
    }
    
    public KlantModel selectKlant() {
        List klantList = klantModel.fetchKlantList();
        klantView.showKlantList(klantList);
        klantView.showSelectKlant();        
        return selectKlantListener(klantList);
    }
/*
    private void selectKlantToModifyListener() {
        String in = ConsoleInput.notEmptyTextInput();

        if (in.equalsIgnoreCase("n")) 
            return;
        
        if(!Validator.isValidInt(in)) {
            klantView.showSelectKlantfailed();
            selectKlantToModify();
        } else {
            int id = Integer.parseInt(in);

            KlantModel klant = new KlantModel(klantModel.getKlantPojoById(id));
            if (klant == null) {
                klantView.showSelectKlantfailed();
                selectKlantToModify();
            } else {
                KlantController skc = new KlantController(klant, new KlantView());
                skc.modifyKlant();        
            }
        }
    }*/
    
    private KlantModel selectKlantListener(List<KlantPojo> klantList) {
        String in = ConsoleInput.notEmptyTextInput();
        if (in.equalsIgnoreCase("n")) 
            return null;
        
        if(!Validator.isValidInt(in)) {
            klantView.showSelectKlantfailed();
            return selectKlantListener(klantList);
        } else {
            int id = Integer.parseInt(in) -1;
            KlantModel klant = null;
            if(klantList.size() >= id ) {
                klant = new KlantModel(klantList.get(id));
            }

            if (klant == null ) {
                klantView.showSelectKlantfailed();
                return selectKlantListener(klantList);
            } else {
                return klant;            
            }
        }
    }
/*
    private void selectKlantToDeleteListener() {
        String in = ConsoleInput.notEmptyTextInput();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        if(!Validator.isValidInt(in)) {
            selectKlantToDelete();
        } else {
            int id = Integer.parseInt(in);

            KlantModel klant = new KlantModel(klantModel.getKlantPojoById(id));
            if (klant == null) {
                klantView.showValidNumber();
                selectKlantToDeleteListener();
                //selectKlantToDelete();
            } else {
                KlantController skc = new KlantController(klant, new KlantView());
                skc.deleteKlant();
            }
        }
    }    
    */
}
