/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.klant;

import com.boerpiet.utility.ConfigVars;

/**
 *
 * @author Sonja UsageExample: AccountDAO dao =
 * KlantDAOFactory.getKlantDAO();
 */
public class KlantDAOFactory {
   
    public static KlantDAO getKlantDAO() {
        /*
        String type = ConfigVars.getDbType();
        if (type.equals("MySQL")) {
            return new MySQLKlantDAO();
        } else if (type.equals("Firebird")) {
            return new FirebirdKlantDAO();
        }
        return new MySQLKlantDAO(); */
        return new KlantDAO();
    }

}
