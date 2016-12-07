/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.account;


import com.boerpiet.dao.Connector;
import com.boerpiet.domeinapp.AccountPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public abstract class AccountDAO {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract String getCreateAccountSQL();
    
    protected abstract String getUpdateAccountByIdSQL();
    
    protected abstract String getAccountByIdSQL();
    
    protected abstract String getFillAccountPojoByUsernamePasswordSQL();
    
    protected abstract String getAllAccountsSQL();
    
    protected abstract String getDeleteAccountByIdSQL();
    
    protected abstract String getDeleteAccountsByKlantIdSQL();
    
    protected abstract String getUserExistsSQL();
    
    /**
     * 
     * @param accountPojo 
     * @return Boolean true on success
     */
    public boolean createAccount(AccountPojo accountPojo) {
        try (Connection conn = Connector.getConnection()){
            String sql =  getCreateAccountSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);        
            
            pstmt.setString(1, accountPojo.getGebruikersnaam());
            pstmt.setString(2, accountPojo.getWachtwoord());
            pstmt.setString(3, accountPojo.getAccountStatus());
                       
            if(accountPojo.getKlantId() == 0)
                pstmt.setNull(4, java.sql.Types.INTEGER);
            else 
                pstmt.setInt(4, accountPojo.getKlantId());
            pstmt.setString(5, "0"); 

            if( pstmt.executeUpdate() == 0)
                throw new Exception("No account created.");
            
            return true;

        } catch (Exception ex) {
            logger.error("create customer failed: " + ex );
            return false;
        }        
    }    
    /**
     * Deletes all the accounts attached with a specific klantId
     * @param id
     * @return 
     */
    public boolean deleteAccountsByKlantId(int id) {
        try(Connection conn = Connector.getConnection()) {
        String sql = getDeleteAccountsByKlantIdSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, id); 
            int insertId = pstmt.executeUpdate();
            
            if(insertId == 0)
                throw new Exception("Delete account failed.");
            
            return true;            
        } catch (Exception ex) {
            logger.error("delete account failed: " + ex);
            return false;
        }
    }    
    
    /**
     * 
     * @param accountPojo AccountPojo
     * @return true on success 
     * Based on the idAccount in the AccountPojo the account in the database is updated.
     * if klantId in model is 0, no klantId is given to the db.
     */
    public boolean updateAccountById(AccountPojo accountPojo) {
        try (Connection conn = Connector.getConnection();) {
            String sql  = getUpdateAccountByIdSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountPojo.getGebruikersnaam()); 
            pstmt.setString(2, accountPojo.getWachtwoord());
            pstmt.setString(3, accountPojo.getAccountStatus());
            if(accountPojo.getKlantId()== 0)
                pstmt.setNull(4, java.sql.Types.INTEGER);
            else 
                pstmt.setInt(4, accountPojo.getKlantId());
            
            String deleted = accountPojo.isDeleted() ? "1" : "0";
            pstmt.setString(5, deleted);
            pstmt.setInt(6, accountPojo.getIdAccount());
            
            pstmt.executeUpdate();
            return true;

        } catch (Exception ex) {
            logger.error("updateAccountById failed:" + ex);
            return false;
        }
    }
    
    public boolean deleteAccount(AccountPojo accountPojo) {
        return deleteAccountById(accountPojo.getIdAccount());
    }
    
    public boolean deleteAccountById(int id) {
        try (Connection conn = Connector.getConnection();) {
        String sql  = getDeleteAccountByIdSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "" + id);
            
            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            logger.error("delete account failed :" + ex);
            return false;
        }
    }
    
    // Returns null if no account with that id is found.
    public AccountPojo getAccountById(int accountId) {

        try (Connection conn = Connector.getConnection();) {
            String sql = getAccountByIdSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);    
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            AccountPojo ap = new AccountPojo(); 
            tryFillPojo(rs, ap);
            return ap;
        } catch (Exception ex) {
            logger.info("get account by Id found no account  :" + ex);
            return null;
        }
    }

    public boolean fillAccountPojoByUsernamePassword(AccountPojo accountPojo) {
         try (Connection conn = Connector.getConnection();) {
            String sql =  getFillAccountPojoByUsernamePasswordSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountPojo.getGebruikersnaam());
            pstmt.setString(2, accountPojo.getWachtwoord());
            
            ResultSet rs = pstmt.executeQuery();
            
            return tryFillPojo(rs, accountPojo);

        } catch (Exception ex) {
            logger.warn("failed login :" + ex);
            return false;
        }
    }

    public ArrayList<AccountPojo> getAllAccounts() {
        ArrayList<AccountPojo> list = new ArrayList<>();

        try (Connection conn = Connector.getConnection();) {
            String sql = getAllAccountsSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);          
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                AccountPojo ap = new AccountPojo(); 
                fillPojo(rs, ap);
                list.add(ap);
            }
        } catch (Exception ex) {
            logger.error("FireBirdAccountDao.getAllAccounts() :" + ex);
        }
        
        return list;
    }

    
    protected boolean tryFillPojo(ResultSet result, AccountPojo accountPojo) {
        try {
            result.next();
            fillPojo(result, accountPojo);
            return true; 
        } catch (SQLException ex) { 
            logger.error("AccountDAO.tryFillPojo() failed:" + ex);
            return false;
        }
    }
    
    // changes a single column row from the account table into an accountPojo object
    // uses accountPojo as input parameter so you can also use it to update an
    // existing pojo.
    protected void fillPojo(ResultSet result, AccountPojo accountPojo) throws SQLException {
        accountPojo.setIdAccount(result.getInt("idAccount"));
        accountPojo.setGebruikersnaam(result.getString("Gebruikersnaam"));
        accountPojo.setWachtwoord(result.getString("Wachtwoord"));
        accountPojo.setAccountStatus(result.getString("Accountstatus"));
        accountPojo.setKlantId(result.getInt("KlantId"));
        accountPojo.setDeleted(result.getBoolean("Deleted"));    
        accountPojo.setDatum_aanmaak(result.getDate("Datum_Aanmaak"));       
    }
    
    protected String createCalendarString(GregorianCalendar cal) {
        int month = cal.get(Calendar.MONTH) +1;
        String datum = "" + cal.get(Calendar.YEAR) + "-"
                          + month + "-"
                          + cal.get(Calendar.DAY_OF_MONTH);
        return datum;
    }

    public boolean userExists(String username) throws SQLException {
        try (Connection conn = Connector.getConnection();) {
            String sql = getUserExistsSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);   
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Return true if there's a result so user exists. Return false if there's no such user.
            return rs.next();
            
        } catch (Exception ex) {
            logger.error("FireBirdAccountDao.getAllAccounts() :" + ex);
            throw new SQLException("");
        }
    }
}
