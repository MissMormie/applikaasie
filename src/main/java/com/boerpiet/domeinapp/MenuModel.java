/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.controllerapp.*;
import com.boerpiet.viewapp.*;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Sonja
 */
public class MenuModel {
    
    

    // ------------ VARIABLES ---------------------------------

    private NodeList menu;
    private Document doc;
    private int menuId;
    private LoginManager loginManager;

    // ------------ CONSTRUCTORS ---------------------------------

    public MenuModel(LoginManager loginManager) {
        this.loginManager = loginManager;
        try {
            File xmlFile = new File("xml/menu.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
        } catch (Exception ex) {
            System.out.println("errors" + ex);
        }        
    }
    
    // ------------ Getters and Setters ---------------------------------
       
    public boolean isLoggedIn() {
        return loginManager.isLoggedIn();
    }
    
    // ------------ PUBLIC FUNCTIONS ---------------------------------
    
    public boolean chooseMenuItem(int menuNumber) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            String currentMenu ="";
            if (menuId != 0)
                currentMenu = "/menuItem[@id=\"" + menuId + "\"]";
            
            String ex4 = "//mainMenu[@login='" + loginManager.getAccountStatus() + "']" + currentMenu + "/menuItem[@number=\"" + menuNumber + "\"]";
            XPathExpression expr = xpath.compile(ex4);

            NodeList resultNode = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            // Check if there is a node with the selected number.
            if(resultNode.getLength() == 0)
                return false;

            String action = resultNode.item(0).getAttributes().getNamedItem("action").getNodeValue();
            String id = resultNode.item(0).getAttributes().getNamedItem("id").getNodeValue();
            int intId = Integer.parseInt(id);
            doAction(action,intId);
            
        } catch (XPathExpressionException ex) {
            return false;
        }
        return true;
    }
    
    public NodeList getMenuNode() {
        String currentMenu ="";
            if (menuId != 0)
                currentMenu = "/menuItem[@id=\"" + menuId + "\"]";
            
        XPath xpath = XPathFactory.newInstance().newXPath();
        String ex = "//mainMenu[@login='" + loginManager.getAccountStatus() + "']" 
                    + currentMenu + "/menuItem";

        try {
            XPathExpression expr = xpath.compile(ex);
            Object exprResult = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) exprResult;
            return nodeList;
        } catch (Exception excep) {
            return null;
        }
    }
    
    // ------------ PRIVATE FUNCTIONS ---------------------------------    

    private void setSubmenu(int id) {
        menuId = id;
    }

    private void setHoofdmenu() {
        menuId = 0;
    }
    
    private void logout() {
        loginManager.logout();
    }
        
    private void doAction(String action, int id) {
        switch(action){
            // CHANGE MENU
            case "submenu": setSubmenu(id); break;
            case "hoofdmenu": setHoofdmenu(); break;
            
            // ACCOUNTS
            case "nieuwAccount": // New Account
                AccountController ac = new AccountController(new AccountModel(), new AccountView());
                ac.newAccount();
                break;
            case "wijzigAccount": // Change Account
                AccountController ac2 = new AccountController(new AccountModel(), new AccountView());
                ac2.selectAccountToModify();
                break;
            case "verwijderAccount": // Delete Account
                AccountController ac3 = new AccountController(new AccountModel(), new AccountView());
                ac3.deleteAccount();
                break;
                
            // KLANTEN
            case "nieuweKlant": // new Klant
                KlantController kc1 = new KlantController(new KlantModel(), new KlantView());
                kc1.newKlant();
                break;
            case "wijzigKlant": // Modify klant
                KlantenController kc2 = new KlantenController(new KlantenModel(), new KlantenView());
                kc2.selectKlantToModify();
                break;
            case "verwijderKlant": // Delete Klant
                KlantenController kc3 = new KlantenController(new KlantenModel(), new KlantenView());
                kc3.selectKlantToDelete();                
                break;
                
            // LOGOUT
            case "logout": logout(); break;
        }
                
    }
}
