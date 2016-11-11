/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Klant;

/**
 *
 * @author Sonja UsageExample: AccountDAO dao =
 * KlantDAOFactory.getKlantDAO("MySQL");
 */
public class KlantDAOFactory {
    // TODO get this variable from console input or config.
    private final static String type = "MySQL";
    
    /**
     * @deprecated Use getKlantDAO() instead.
     * @param type
     * @return 
     */
    public static KlantDAO getKlantDAO(String type) {
        if (type.equals("MySQL")) {
            return new MySQLKlantDAO();
        } else if (type.equals("Firebird")) {
            return new FirebirdKlantDAO();
        }
        return new MySQLKlantDAO();
    }

    public static KlantDAO getKlantDAO() {

        if (type.equals("MySQL")) {
            return new MySQLKlantDAO();
        } else if (type.equals("Firebird")) {
            return new FirebirdKlantDAO();
        }
        return new MySQLKlantDAO();
    }

}
