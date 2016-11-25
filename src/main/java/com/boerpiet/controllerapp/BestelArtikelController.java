/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.BestelArtikelModel;
import com.boerpiet.domeinapp.Validator;
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
    
    public int inputOrderArticleId (int bestelId) {
        bav = new BestelArtikelView ();
        bav.showInputOrderArticleId();
        int oAId = inputIntPositiveAndInDatabaseCheck (bestelId);
        return oAId;
    }
    
    private int inputIntCheck () {
        
        bav = new BestelArtikelView ();
        String intInput = input.nextLine();
        if (Validator.isValidInt(intInput)) {
            return Integer.parseInt(intInput);
        } else {
            bav.showGiveNumber ();
            return inputIntCheck ();
        }
    }
    
    public int inputIntPositiveAndInDatabaseCheck (int bestelId) {
        int aId = inputIntCheck();
        if (Validator.isPositiveInt(aId) && bam.checkBestelRegelId(aId, bestelId)) {
            return aId;
        } else {
            bav.showGiveNumber();
            return inputIntPositiveAndInDatabaseCheck (bestelId);
        }
    }
    
}