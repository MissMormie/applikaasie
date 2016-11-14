/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant;

import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public abstract class KlantDAO {

    /**
     *
     * @param klant KlantModel
     * @return boolean true on successful creation
     */
    public abstract boolean createKlant(KlantModel klant);    

    public abstract ArrayList<KlantPojo> getAllKlanten();

    /**
     *
     * @param id
     * @return
     */
    public abstract KlantModel getKlantById(int id); 

    public abstract boolean updateKlantById(KlantPojo klant); 

    public abstract boolean deleteKlantById(int id);
    
    public abstract boolean deleteKlant(KlantModel klant);
}
