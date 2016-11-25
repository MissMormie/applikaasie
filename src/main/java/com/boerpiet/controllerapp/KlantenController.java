/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.KlantenView;
import com.boerpiet.viewapp.KlantView;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class KlantenController {
    private final KlantenModel klantModel;
    private final KlantenView klantView;
    private final Scanner input = new Scanner(System.in);
    
    public KlantenController(KlantenModel klantModel, KlantenView klantView) {
        this.klantModel = klantModel;
        this.klantView = klantView;
    }

    public void selectKlantToModify() {
        klantView.showKlantList(klantModel.fetchKlantList());
        klantView.showSelectKlantToModify();
        selectKlantToModifyListener();
    }

    public void selectKlantToDelete() {
        klantView.showKlantList(klantModel.fetchKlantList());
        klantView.showSelectKlantToDelete();
        selectKlantToDeleteListener();
    }
    
    public KlantModel selectKlant() {
        klantView.showKlantList(klantModel.fetchKlantList());
        klantView.showSelectKlant();        
        return selectKlantListener();
    }

    private void selectKlantToModifyListener() {
        String in = textListener();

        if (in.equalsIgnoreCase("n")) 
            return;
        
        if(!Validator.isValidInt(in)) {
            klantView.showSelectKlantfailed();
            selectKlantToModify();
        } else {
            int id = Integer.parseInt(in);

            KlantModel klant = klantModel.getKlantById(id);
            if (klant == null) {
                klantView.showSelectKlantfailed();
                selectKlantToModify();
            } else {
                KlantController skc = new KlantController(klant, new KlantView());
                skc.modifyKlant();        
            }
        }
    }
    
    private KlantModel selectKlantListener() {
        String in = textListener();
        if (in.equalsIgnoreCase("n")) 
            return null;
        if(!Validator.isValidInt(in)) {
            klantView.showSelectKlantfailed();
            return selectKlantListener();
        } else {
            int id = Integer.parseInt(in);

            KlantModel klant = klantModel.getKlantById(id);
            if (klant == null ) {
                klantView.showSelectKlantfailed();
                return selectKlantListener();
            } else {
                return klant;            
            }
        }
    }

    private void selectKlantToDeleteListener() {
        String in = textListener();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        if(!Validator.isValidInt(in)) {
            selectKlantToDelete();
        } else {
            int id = Integer.parseInt(in);

            KlantModel klant = klantModel.getKlantById(id);
            if (klant == null) {
                selectKlantToDelete();
            } else {
                KlantController skc = new KlantController(klant, new KlantView());
                skc.deleteKlant();
            }
        }
    }

    private String textListener() {
        String text = input.nextLine();
        if(text.isEmpty())
            return textListener();
        return text;
    }    
    
    private int numberListener() {
        String number = input.nextLine();
        if(Validator.isValidInt(number))
            return Integer.parseInt(number);
        
        klantView.showValidNumber();
        return numberListener();
    }
    
}
