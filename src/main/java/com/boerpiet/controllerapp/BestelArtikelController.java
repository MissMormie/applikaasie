/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.BestelArtikelModel;
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
            return inputIntCheck (string);
        }
    }
    
    public int inputOAIdInDatabaseCheck (int bestelId) {
        bav = new BestelArtikelView ();
        bam = new BestelArtikelModel ();
                
        String bId = input.nextLine();
        int id = inputIntCheck(bId);
        
        if (bam.checkOAIdInDataBase(bestelId, id)) {
            return id;
        } else {
            bav.showOAIdNotBelongingToBestelId ();
            return inputOAIdInDatabaseCheck (bestelId);
        }
    }
}