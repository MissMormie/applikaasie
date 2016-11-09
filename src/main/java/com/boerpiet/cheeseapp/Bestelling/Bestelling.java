/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Peaq
 */
public class Bestelling {
    private Connector conn = new Connector();
    
    public void create (Object order) {
        String insertString = "INSERT INTO Bestelling"+ 
                "(Klantkey, BestelDatum, AccountKey, Deleted) VALUES"
                + "(?,?,?,?)";
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(insertString)){
            prepStatement.setInt(1, bestelling.klantKey);   //heeft Order instantie (constructor
                                            // met vier parameters en user input) nodig
            prepStatement.setLocalDate (2, bestelling.bestelDatum);
            prepStatement.setInt (3, bestelling.accountKey);
            prepStatement.setBoolean (4, false);
            prepStatement.executeUpdate();        
    }
    catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
    }
    }
    public Order read (int id) {
        //class die ReadOrderDao.getOrder aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        Order o = new Order();
        String queryString = "SELECT * FROM Bestelling" + "WHERE idBestelling = "+id;
        try (Connection orderCon = conn.getConnection();
                PreparedStatement prepStatement = orderCon.prepareStatement(queryString)){
            prepStatement.setInt(1, id);   //Weet niet zeker of dit nodig is
            //prepStatement.setInt (2, ?o.klant); // hoe krijg ik hier de gegevens uit de db?
            //prepStatement.setLocalDate (3, ?o.bestelDatum); //
            //prepStatement.setInt (4, ?o.account);
            //prepStatement.setBoolean (5, false);
            ResultSet rs = prepStatement.executeQuery();
            // moet rs ook gesloten worden?
            if (rs != null){
            o.setBestelDatum (rs.getDate("bestelDatum").toLocalDate());
            o.setDeleted (rs.getBoolean(5));
            }
            else {
                System.out.println("Geen bestellingen in de database");
            }
        }
        catch (SQLException|ClassNotFoundException ex) {
            ex.printStackTrace();
    } return o;
  }
    public void update (Object order) {
        String updateString = "UPDATE Bestelling SET "
                + "(KlantKey, BestelDatum, AccountKey, Deleted) VALUES"
                +"(?,?,?,?) WHERE idBestelling = "+order.id; // van parameter object in update
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(updateString)){
            prepStatement.setInt(1, o.id);   //heeft Bestelling instantie (constructor
                                            // met vijf parameters en user input) nodig
            prepStatement.setInt(2, o.klant);
            prepStatement.setLocalDate(3, o.bestelDatum);
            prepStatement.setInt (4, o.account);
            prepStatement.setBoolean(5, false);
            prepStatement.executeUpdate();
        }
        catch (SQLException|ClassNotFoundException e) {
    e.printStackTrace();
    }
    }
    public void delete (int id) {
        //class die DeleteOrderDao.delete aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        String deleteString = "DELETE * FROM Bestelling" + "WHERE idBestelling = "+id;
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(deleteString)){
            
            // eventueel nog een bevestiging vragen van user voor deletion?
            
            prepStatement.executeUpdate ();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                }
    }    
}

