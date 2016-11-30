/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.ConfModel;
import com.boerpiet.domeinapp.LoginManager;
import com.boerpiet.utility.ConsoleInput;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.ConfView;

/**
 *
 * @author Sonja
 */
public class ConfController {
    ConfView cv;
    ConfModel cm;
    LoginManager lm;

    public ConfController(ConfModel cm, ConfView cv, LoginManager lm) {
        this.cv = cv;
        this.cm = cm;
        this.lm = lm;
    }

    public void modifyDatabaseSetting() {
        cv.showPickDatabase(cm.getDatabaseNodes());

        String input = ConsoleInput.textInput();
        
        if (input.equalsIgnoreCase("n"))
            return;

        if(!Validator.isValidInt(input)) {
            cv.showInvalidInput();
            modifyDatabaseSetting();
            return;
        }
            
        int database = Integer.parseInt(input);
        
        // -1 omdat er voor de view gewerkt wordt vanaf 1, maar de xml werkt vanaf 0
        if(!cm.setDefaultDatabase(database - 1)) {
            cv.showModifyDatabaseFailed();
            modifyDatabaseSetting();      
            return;
        }
        
        cv.showDatabaseChanged();
        lm.logout();
    }

    public void modifyConnectionPoolSetting() {
        cv.showPickConnectionPool(cm.getConnectionPoolNodes());

        String input = ConsoleInput.textInput();
        
        if (input.equalsIgnoreCase("n"))
            return;

        if(!Validator.isValidInt(input)) {
            cv.showInvalidInput();
            modifyConnectionPoolSetting();
            return;
        }
            
        int connection = Integer.parseInt(input);
        
        // -1 omdat er voor de view gewerkt wordt vanaf 1, maar de xml werkt vanaf 0
        
        if(!cm.setDefaultConnection(connection - 1)) {
            cv.showModifyConnectionPoolFailed();
            modifyConnectionPoolSetting();      
            return;
        }
        
        cv.showConnectionPoolChanged();
        System.exit(0);
    }
}
