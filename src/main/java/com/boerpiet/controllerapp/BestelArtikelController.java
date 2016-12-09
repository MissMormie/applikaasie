/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.BestelArtikelModel;
import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.BestelArtikelView;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestelArtikelController {
    
    private BestelArtikelModel bam;
    private BestelArtikelView bav;
    private BestellingModel bm;
    private final Scanner input = new Scanner (System.in);
    
    public BestelArtikelController (BestelArtikelModel bam) {
        this.bam = bam;
    }

    BestelArtikelController() {
    }
    
    //bestelregels worden OA genoemd in klassen
    //methods to check input for validity
    private int inputIntCheck (String string) {
        
        bav = new BestelArtikelView ();
        
        if (Validator.isValidInt(string)) {
            return Integer.parseInt(string);
        } else {
            bav.showGiveNumber ();
            return inputIntCheck (input.nextLine());
        }
    }
    
    public int inputOAIdInDatabaseCheck (int bestelId) {
        bav = new BestelArtikelView ();
        bam = new BestelArtikelModel ();
        bm = new BestellingModel ();
                
        String stringBestelRegellId = input.nextLine();
        int bestelregelId = inputIntCheck(stringBestelRegellId);
        
        if (bm.checkOAIdBelongsToOrderId(bestelId, bestelregelId)) {
            return bestelregelId;
        } else {
            bav.showOAIdNotBelongingToBestelId ();
            return inputOAIdInDatabaseCheck (bestelId);
        }
    }
}