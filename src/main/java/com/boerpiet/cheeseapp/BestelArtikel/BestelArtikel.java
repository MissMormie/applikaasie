/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Peaq
 */
public class BestelArtikel {
    private Connector conn = new Connector();
    
    void create (OrderArticle orderArticle) {
        String insertString = "INSERT INTO BestelArtikel"+
                "(BestellingId, ArtikelId, Aantal, Deleted) VALUES"
                + "(?,?,?,?)";
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(insertString)){
            prepStatement.setInt(1, bestelArtikel.bestellingId);
            //heeft OrderArticle instantie (constructor met vier parameters en user input) nodig
            prepStatement.setInt (2, bestelArtikel.artikelId);
            prepStatement.setInt(3, bestelArtikel.aantal);
            prepStatement.setBoolean (4, false);
            prepStatement.executeUpdate();        
    }
    catch (SQLException|ClassNotFoundException e) {
    e.printStackTrace();
    }
    }
    OrderArticle read (int id) {
        //class die ReadOrderArticleDao.getOrderArticle aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        OrderArticle ba = new OrderArticle ();
        String queryString = "SELECT * FROM BestelArtikel" + "WHERE idBestelArtikel = "+id;
        try (Connection orderArticleCon = conn.getConnection();
                PreparedStatement prepStatement = orderArticleCon.prepareStatement(queryString)){
            prepStatement.setInt(1, id);   //Weet niet zeker of dit nodig is
            //prepStatement.setInt (2, ?ba.aantal); // hoe krijg ik hier de gegevens uit de db?
            //prepStatement.setInt (3, ?ba.bestelId);
            //prepStatement.setInt (4, ?ba.artikelId);
            //prepStatement.setBoolean (5, false);
            ResultSet rs = prepStatement.executeQuery();
            // moet rs ook gesloten worden?         
            if (rs != null){
            ba.setAantal (rs.getInt(2));
            ba.setDeleted (rs.getBoolean (5));
            }
            else {
                System.out.println("Geen bestelregels in de database");
            }
        }
        catch (SQLException|ClassNotFoundException ex) {
            ex.printStackTrace();
        } return ba;
  }
    void update (OrderArticle orderArticle) {
        String updateString = "UPDATE BestelArtikel SET "
                + "(Aantal, bestelId, artikelId, deleted) VALUES "
                +"(?,?,?,?) WHERE bestelArtikel = "+orderarticle.id; //van paramater object
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(updateString)){
            prepStatement.setInt(1, a.id);   //heeft OrderArticle instantie (constructor
                                            // met vijf parameters en user input) nodig
            prepStatement.setInt (2, a.aantal);
            prepStatement.setInt (3, a.bestelId);
            prepStatement.setInt (4, a.artikelId);
            prepStatement.setBoolean(5, false);
            prepStatement.executeUpdate();
        }
        catch (SQLException|ClassNotFoundException e) {
    e.printStackTrace();
    }
    }
    void delete (int id) {
        //class die DeleteOrderArticleDao.delete aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        String deleteString = "DELETE * FROM BestelArtikel" + "WHERE idBestelArtikel = "+id;
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(deleteString)){
            
            // eventueel nog een bevestiging vragen van user voor deletion?
            
            prepStatement.executeUpdate ();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                }
        }
}

}
