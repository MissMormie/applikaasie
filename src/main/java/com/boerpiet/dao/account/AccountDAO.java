/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.account;

import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.HibernateUtil;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Sonja
 */
public class AccountDAO {

    public boolean createAccount(AccountPojo accountPojo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
 
        int id = (Integer) session.save(accountPojo);
        session.getTransaction().commit();
        session.close();
        
        return id != 0;
    }

    public boolean deleteAccountsByKlantId(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String hql = "delete FROM AccountPojo WHERE"
                   + "klantId = :klantId";
        
        int result = session .createQuery(hql)
                             .setInteger("klantId", id)
                             .executeUpdate();
        
        session.getTransaction().commit();
        session.close(); 
        // TODO look at this return value, is it necessary
        // should it mean all accounts even if it's 0 have been deleted, or min 1 has been deleted.
        return true;
    }

    public boolean updateAccountById(AccountPojo accountPojo) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        
        session.saveOrUpdate(accountPojo);
        
        session.getTransaction().commit();
        session.close(); 
        
        return true;
    }

    public boolean deleteAccount(AccountPojo accountPojo) {
        accountPojo.setDeleted(true);
        return updateAccountById(accountPojo);
    }

    public boolean deleteAccountById(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String hql = "delete FROM AccountPojo WHERE"
                   + "idAccount = :idAccount";
        
        int result = session .createQuery(hql)
                             .setInteger("idAccount", id)
                             .executeUpdate();
        
        session.getTransaction().commit();
        session.close(); 
        
        return result != 0;
    }

    public AccountPojo getAccountById(int accountId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        AccountPojo ap = (AccountPojo) session.get(AccountPojo.class, accountId);

        session.getTransaction().commit();
        session.close(); 

        return ap;
    }

    public AccountPojo getAccountByUsernamePassword(String username, String password) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
 
        List<AccountPojo> accounts = (List<AccountPojo>) session
               .createQuery("FROM AccountPojo a "
                          + "WHERE a.gebruikersnaam = :gebruikersnaam "
                          + "AND a.wachtwoord = :wachtwoord "
                          + "AND a.deleted = :deleted")
               .setParameter("wachtwoord", password)
               .setParameter("gebruikersnaam", username)
               .setParameter("deleted", false)
               .list();
 
        session.getTransaction().commit();
        session.close(); 
        
        if (accounts.isEmpty())
            return null;
        return accounts.get(0);
    }

    public List<AccountPojo> getAllAccounts() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
 
        List<AccountPojo> accounts = (List<AccountPojo>) session
               .createQuery("FROM AccountPojo a WHERE a.deleted = :deleted")
               .setParameter("deleted", false)
               .list();
 
        session.getTransaction().commit();
        session.close(); 
        
        return accounts;
    }

    public boolean userExists(String username) throws SQLException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
 
        List<AccountPojo> accountList = (List<AccountPojo>) session
               .createQuery("FROM AccountPojo a WHERE a.deleted = :deleted AND a.gebruikersnaam = :gebruikersnaam")
               .setBoolean("deleted", false)
               .setString("gebruikersnaam", username)
               .list();
 
        session.getTransaction().commit();
        session.close(); 
     
        return !accountList.isEmpty();
    }
}
