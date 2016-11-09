/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet;

import com.boerpiet.cheeseapp.account.AccountDAOFactory;
import com.boerpiet.cheeseapp.account.AccountDAO;

/**
 *
 * @author Sonja
 */
public class mainTest {
    public static void main(String[] args) {
        AccountDAO dao = AccountDAOFactory.getAccountDAO("MySQL");
        dao.
    }
}
