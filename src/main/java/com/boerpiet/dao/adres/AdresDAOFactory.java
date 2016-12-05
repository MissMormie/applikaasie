/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.adres;

import com.boerpiet.utility.ConfigVars;

/**
 *
 * @author Sonja
 */
public class AdresDAOFactory {
    public static AdresDAO getAdresDAO(){
        /*
        String type = ConfigVars.getDbType();
        if (type.equals("MySQL")) {
            return new AdresMySQLDAO();
        } else if (type.equals("Firebird")) {
                return new AdresFirebirdDAO();
        }
        return new AdresMySQLDAO();
        */
        return new AdresDAO();
    }
}

