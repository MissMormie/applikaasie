/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.klant.KlantDAOFactory;
import java.util.List;
/**
 *
 * @author Sonja
 */
public class KlantenModel {

    
    public List<KlantPojo> fetchKlantList() {
        return KlantDAOFactory.getKlantDAO().getAllKlanten();
    }

    public KlantPojo getKlantPojoById(int id) {
        return KlantDAOFactory.getKlantDAO().getKlantById(id);  
    }
}
