/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant;

import com.boerpiet.domeinapp.ConfigVars;

/**
 *
 * @author Sonja UsageExample: AccountDAO dao =
 * KlantDAOFactory.getKlantDAO();
 */
public class KlantDAOFactory {
    // TODO get this variable from console input or config.
   
    public static KlantDAO getKlantDAO() {
        String type = ConfigVars.getDbType();
        if (type.equals("MySQL")) {
            return new MySQLKlantDAO();
        } else if (type.equals("Firebird")) {
            return new FirebirdKlantDAO();
        }
        return new MySQLKlantDAO();
    }

}
