/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.viewapp.BestelArtikelView;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestelArtikelModel {
    
    private BestelArtikelView bav;
    private BestelArtikelModel bam;
    private final Scanner input = new Scanner (System.in);
    
    public int inputOrderArticleId () {
        bav.showInputOrderArticleId();
        int bestelregel = Integer.parseInt(input.nextLine());
        return bestelregel;
    }
    
}
