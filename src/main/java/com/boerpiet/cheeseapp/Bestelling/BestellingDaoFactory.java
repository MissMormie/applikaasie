/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;


/**
 *
 * @author Peaq
 */
public class BestellingDaoFactory {
    public static SuperBestellingDao getBestellingDAO(String type){
            if (type.equals("MySQL")) {
                    return new SqlBestellingDao();
            } else if (type.equals("Firebird")) {
                    return new FirebirdBestellingDao();
            } 
            return new SqlBestellingDao();
    }    
}
