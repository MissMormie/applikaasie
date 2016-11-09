/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.ArtikelModel;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class ArtikelDao extends SuperArtikelDao {
    
    //try (Connection artikelCon = conn.getConnection();
    //     PreparedStatement prepStatement = artikelCon.prepareStatement(insertString)){
    
    Article read (int id) {
        //class die ReadArticleDao.getArticle aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        Article a = new Article();
        String queryString = "SELECT * FROM Artikel" + "WHERE ArtikelId = "+id;
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(queryString)){
            prepStatement.setInt(1, id);   //Weet niet zeker of dit nodig is
            //prepStatement.setString(2, ?a.naam); // hoe krijg ik hier de gegevens uit de db?
            //prepStatement.setDouble(3, ?a.prijs);
            //prepStatement.setInt (4, ?a.voorraad);
            ResultSet rs = prepStatement.executeQuery();
            // moet rs ook gesloten worden?
            if (rs != null){
            a.setNaam (rs.getString(2));
            a.setPrijs (rs.getDouble(3));
            a.setVoorraad (rs.getInt(4));
            }
            else {
                System.out.println("Geen artikelen in de database");
            }
        }
        catch (SQLException|ClassNotFoundException ex) {
            ex.printStackTrace();
        } return a;
  }
    void update (Article article) {
        String updateString = "UPDATE Artikel SET (Naam, Prijs, Voorraad, Deleted) VALUES "
                +"(?,?,?,?) WHERE idArtikel  ="+article.id; // van parameter in update methode
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(updateString)){
            prepStatement.setInt(1, a.id);   //heeft Article instantie (constructor
                                            // met vijf parameters en user input) nodig
            prepStatement.setString(2, a.naam);
            prepStatement.setDouble(3, a.prijs);
            prepStatement.setInt (4, a.voorraad);
            prepStatement.setBoolean(5, false);
            prepStatement.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
    }
    }
    void delete (int id) {
        //class die DeleteArticleDao.delete aanroept moet (scanner)input
        //toekennen aan id paramater van deze methode
        String deleteString = "DELETE * FROM Artikel" + "WHERE ArtikelId = "+id;
        try (Connection artikelCon = conn.getConnection();
                PreparedStatement prepStatement = artikelCon.prepareStatement(deleteString)){
            // eventueel nog een bevestiging vragen van user voor deletion?
            
            prepStatement.executeUpdate ();
            } catch (SQLException|ClassNotFoundException ex) {
                ex.printStackTrace();
                }
        }

    @Override
    public boolean createArtikel(ArtikelModel artikel) {
        String sql = "INSERT INTO Artikel (id, naam, prijs, voorraad, deleted)"
                + " VALUES ("
                        + "'" + artikel.getId () + "',"
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "',"
                        + "'" + artikel.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(ArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            MySQLConnection.getMySQLConnection().close();
            // is dit de goede plek om de connectie te sluiten?
            // zo ja, hoe een singleton connectie te coderen in try-with-resources?
        }
        return true;
    }

    @Override
    public boolean updateArtikel (ArtikelModel artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteArtikel (ArtikelModel artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidLogin (ArtikelModel artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArtikelModel getArtikel (int artikelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

