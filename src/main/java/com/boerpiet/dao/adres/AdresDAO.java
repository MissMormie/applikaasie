/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.adres;

import com.boerpiet.HibernateUtil;
import com.boerpiet.dao.Connector;
import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.AdresTypePojo;
import com.boerpiet.domeinapp.KlantModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public class AdresDAO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Updates adres based on information in AdresPojo.
     * 
     * @param adres the AdresPojo
     * @return boolean, true on success.
     */
    public boolean updateAdres(AdresPojo adres) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(adres);            
            session.getTransaction().commit();
        }
        
        return true;
    }

    public int createAdres(AdresPojo adres) {
        Integer adresId;
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            adresId = (Integer) session.save(adres);
            session.getTransaction().commit();
        }
        return adresId;
    }
    
    public AdresTypePojo getAdresTypePojo(String adresType) {
        List result;
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "FROM AdresTypePojo WHERE "
                    + "soort = :soort";
            result = session .createQuery(hql)
                    .setParameter("soort", adresType)
                    .list();
            session.getTransaction().commit();
        }
        
        if(result.isEmpty())
            return null;
        return (AdresTypePojo) result.get(0);
    }
    
    public  boolean deleteAdres(AdresPojo adres) {
        adres.setDeleted(true);
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(adres);            
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
}