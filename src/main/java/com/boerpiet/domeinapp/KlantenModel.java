/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.klant.KlantDAOFactory;
import java.util.ArrayList;
/**
 *
 * @author Sonja
 */
public class KlantenModel {

    
    public ArrayList<KlantPojo> fetchKlantList() {
        return KlantDAOFactory.getKlantDAO().getAllKlanten();
    }

    public KlantModel getKlantById(int id) {
        return KlantDAOFactory.getKlantDAO().getKlantById(id);  
    }
}
