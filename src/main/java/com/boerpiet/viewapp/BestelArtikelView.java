/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

/**
 *
 * @author Peaq
 */
public class BestelArtikelView {
    
    public void showInputOAIdToModify () {
        System.out.println("Geef bestelregelid waar je artikelen van wilt wijzigen:");
    }
    public void showInputOAIdToDelete () {
        System.out.println("Geef bestelregelid waar je artikelen van wilt verwijderen:");
    }
    public void showGiveNumber() {
        System.out.println("Geef een geldig nummer of aantal (hele, positieve getallen)");
    }
    public void showAddOASuccess () {
        System.out.println("Artikel is toegevoegd aan bestelling.");
    }
    public void showDeleteOASuccess () {
        System.out.println("Artikel is verwijderd van bestelling.");
    }
    public void showOAIdNotBelongingToBestelId() {
        System.out.println("Ingevoerde bestelregel hoort niet bij opgegeven bestelid.");
        System.out.println("Geef bestelregelid: ");
    }
}
