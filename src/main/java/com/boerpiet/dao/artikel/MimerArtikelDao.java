/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.artikel;

/**
 *
 * @author Peaq
 */
public class MimerArtikelDao extends SuperArtikelDao {
    //utility strings
    @Override
    protected String getIsArtikelInDatabaseSQL () {
        return "SELECT idArtikel FROM Artikel WHERE idArtikel = ?";
    }
    
    @Override
    protected String getArtikelByIdSQL () {
        return "SELECT * FROM Artikel WHERE Deleted = false AND idArtikel = ?;";
    }
    
    @Override
    protected String getMaxArtikelIdSQL () {
        return "SELECT idArtikel FROM Artikel WHERE idArtikel = (SELECT MAX(idArtikel) FROM Artikel);";
    }
    
    @Override
    protected String getArtikelForArrayListSQL () {
        return "SELECT * FROM Artikel WHERE Deleted = false;";
    }
    
    //CRUD strings    
    @Override
    protected String getCreateArtikelMySQL () {
        throw new UnsupportedOperationException ("Not valid here.");
    }
    
    @Override
    protected String getCreateArtikelMimerSQL () {
        return "INSERT INTO Artikel (idArtikel, Naam, Prijs, Voorraad) VALUES (?, ?, ?, ?);";
    }
    
    @Override
    protected String getUpdateArtikelNPVSQL () {
        return "UPDATE Artikel SET (Naam, Prijs, Voorraad) = (?, ?, ?) WHERE idArtikel = ?;";
    }
    
    @Override
    protected String getUpdateArtikelNaamSQL () {
        return "UPDATE Artikel SET Naam = ? WHERE idArtikel = ?;";
    }
    
    @Override
    protected String getUpdateArtikelPrijsSQL () {
        return "UPDATE Artikel SET Prijs = ? WHERE idArtikel = ?;";
    }
    
    @Override
    protected String getUpdateArtikelVoorraadSQL () {
        return "UPDATE Artikel SET Voorraad = ? WHERE idArtikel = ?;";
    }   

    @Override
    protected String getDeleteArtikelSQL () {
        return "UPDATE Artikel SET Deleted = true WHERE Deleted = false AND idArtikel = ?;";
    }
 }