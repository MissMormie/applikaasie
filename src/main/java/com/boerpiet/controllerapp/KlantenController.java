/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
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
        String in = input.nextLine();

        if (in.equalsIgnoreCase("n")) 
            return;
        int id = Integer.parseInt(in);
        // validate id
        KlantModel klant = klantModel.getKlantById(id);
        if (klant == null) {
            klantView.showSelectKlantfailed();
            selectKlantToModify();
        }
        else {
            KlantController skc = new KlantController(klant, new KlantView());
            skc.modifyKlant();        
        }
    }
    
    private KlantModel selectKlantListener() {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return null;
        int id = Integer.parseInt(in);

        KlantModel klant = klantModel.getKlantById(id);
        return klant;
    }

    private void selectKlantToDeleteListener() {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
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
