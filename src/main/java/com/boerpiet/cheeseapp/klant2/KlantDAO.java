/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant2;

import com.boerpiet.domeinapp.KlantPojo;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public abstract class KlantDAO {

    public abstract boolean createKlant(KlantPojo klant);    

    public abstract ArrayList<KlantPojo> getAllKlanten();

    /**
     *
     * @param id
     * @return
     */
    public abstract KlantPojo getKlantById(int id); 

    public abstract boolean updateKlantById(KlantPojo klant); 

    public abstract boolean deleteKlantByID(int id);
}
