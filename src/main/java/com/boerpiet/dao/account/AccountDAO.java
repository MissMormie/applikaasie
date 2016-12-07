/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.account;

import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.HibernateUtil;
import java.util.List;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public class AccountDAO {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // ----------------------- CREATE --------------------------------------
    
    /**
     * Creates a database entry for the accountPojo
     * @param accountPojo
     * @return true for success.
     */
    public boolean createAccount(AccountPojo accountPojo) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            int id = (Integer) session.save(accountPojo);
            session.getTransaction().commit();
            return id != 0;
            
        } catch (JDBCException ex) {
            logger.error("" + ex);
            return false;
        }
    }

    // ----------------------- READ   --------------------------------------
    
    public AccountPojo getAccountById(int accountId) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            AccountPojo ap = (AccountPojo) session.get(AccountPojo.class, accountId);
            session.getTransaction().commit();
            return ap;
            
        } catch (JDBCException ex) {
            logger.error("" + ex);
            return null;
        }
    }

    public AccountPojo getAccountByUsernamePassword(String username, String password) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            AccountPojo account = (AccountPojo) session
                    .createQuery("FROM AccountPojo a "
                            + "WHERE a.gebruikersnaam = :gebruikersnaam "
                            + "AND a.wachtwoord = :wachtwoord "
                            + "AND a.deleted = false")
                    .setParameter("wachtwoord", password)
                    .setParameter("gebruikersnaam", username)
                    .setMaxResults(1)
                    .uniqueResult();
            session.getTransaction().commit();
            return account;
            
        } catch (JDBCException ex) {
            logger.error("" + ex);
            return null;
        }
       
    }

    /**
     * Returns a list of all accounts where deleted is false.
     * @return List
     */
    public List<AccountPojo> getAllAccounts() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<AccountPojo> accounts = (List<AccountPojo>) session
                    .createQuery("FROM AccountPojo a WHERE a.deleted = :deleted")
                    .setParameter("deleted", false)
                    .list();
            session.getTransaction().commit();
            return accounts;
        } catch (JDBCException ex) {
            logger.error("" + ex);
            return null;
        } 
    }

    /**
     * The method checks if the username exists in the db. If there's a db problem 
     * it will throw and JDBCException.
     * 
     * @param username
     * @return
     * @throws JDBCException 
     */
    public boolean userExists(String username) throws JDBCException {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "FROM AccountPojo a WHERE a.deleted = :deleted AND a.gebruikersnaam = :gebruikersnaam";

            List<AccountPojo> accountList = (List<AccountPojo>) session
                    .createQuery(hql)
                    .setParameter("deleted", false)
                    .setParameter("gebruikersnaam", username)
                    .list();

            session.getTransaction().commit();
            return !accountList.isEmpty();
            
        } catch (JDBCException ex) {
            logger.error("" + ex);
            throw ex;
        } 
    }

    // ----------------------- UPDATE --------------------------------------
    
    public boolean updateAccount(AccountPojo accountPojo) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(accountPojo);
            session.getTransaction().commit();
            return true;
            
        } catch (JDBCException ex) {
            logger.error("" + ex);
            return false;
        } 
    }

    // ----------------------- DELETE --------------------------------------

    public boolean deleteAccountsByKlantId(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            
            String hql = "update AccountPojo set deleted = true WHERE klantId = :klantId";
            session .createQuery(hql)
                    .setParameter("klantId", id)
                    .executeUpdate();
            
            session.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            logger.error("" + ex);
            return false;
        }
    }

    
    public boolean deleteAccountById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "update AccountPojo set deleted = true WHERE idAccount = :idAccount";
            int result = session .createQuery(hql)
                    .setParameter("idAccount", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return result != 0;

        }  catch (Exception ex) {
            logger.error("" + ex);
            return false;
        }
    }
}
