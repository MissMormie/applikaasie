/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant;

import com.boerpiet.domeinapp.AdresPojo;
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

    /**
     * Fetches a klant list with all current klanten.
     * 
     * @return ArrayList<KlantPojo> list of all klanten
     */
    public abstract ArrayList<KlantPojo> getAllKlanten();

    /**
     * Fetches the klant data and adress information beloning to that klant
     * based on the klant id.
     * 
     * @param id int - the id of existing klant
     * @return KlantModel the filled KlantModel
     */
    public abstract KlantModel getKlantById(int id); 

    /**
     * Updates the klant data from KlantPojo (ie, not the address info) 
     * 
     * @param klant
     * @return boolean, true on success
     */
    public abstract boolean updateKlantById(KlantPojo klant); 

    /**
     * Deletes the complete Klant information including adres based on klantId
     * Use when only the id of the Klant in known.
     * 
     * @param id int - the id of existing klant
     * @return boolean, true on success
     */
    public abstract boolean deleteKlantById(int id);
    

    /**
     * Deletes the complete Klant information including adres based on klantModel
     * Use when the klantModel is completely filled.
     * 
     * @param klant the KlantModel of the klant to be deleted.
     * @return boolean, true on success.
     */
    public abstract boolean deleteKlant(KlantModel klant);

    /**
     * Updates adres based on information in AdresPojo.
     * 
     * @param adres the AdresPojo
     * @return boolean, true on success.
     */
    public abstract boolean updateAdres(AdresPojo adres);
}
