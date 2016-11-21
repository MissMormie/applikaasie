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

    /**
     * Initializes MenuModel and reads menu.xml
     * 
     * @param loginManager -- LoginManager 
     */
    public MenuModel(LoginManager loginManager) {
        this.loginManager = loginManager;
        try {
            // Read in XML file and normalize it.
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
    /**
     * Executes the action choosen in the menu
     * 
     * @param menuNumber the menu number selected.
     * @return true on succes.
     */
    public boolean chooseMenuItem(int menuNumber) {
        String currentMenu ="";
        if (menuId != 0)
            currentMenu = "/menuItem[@id=\"" + menuId + "\"]";
        
        String expression = "//mainMenu[@login='" + loginManager.getAccountStatus() + "']" + currentMenu + "/menuItem[@number=\"" + menuNumber + "\"]";
        XPath xpath = XPathFactory.newInstance().newXPath();            

        try {
            // Get the Nodelist that adheres to the expression
            XPathExpression expr = xpath.compile(expression);
            NodeList resultNode = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            // Check if there is a node with the selected number is found.
            if(resultNode.getLength() == 0)
                return false;

            // Read action and id attributes from selected node
            String action = resultNode.item(0).getAttributes().getNamedItem("action").getNodeValue();
            String id = resultNode.item(0).getAttributes().getNamedItem("id").getNodeValue();
            int intId = Integer.parseInt(id);
            
            // execute the action from the xml.
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
        
    /**
     * Performs the selected action
     * 
     * @param action the action attribute
     * @param id the node id
     */
    private void doAction(String action, int id) {
        switch(action){
            // CHANGE MENU
            case "submenu": setSubmenu(id); break;
            case "hoofdmenu": setHoofdmenu(); break;
            
            
            // ACCOUNTS
            case "nieuwKlantAccount": // New Account
                AccountController ac = new AccountController(new AccountModel(), new AccountView());
                ac.newKlantAccount();
                break;
            case "nieuwMedewerkerAccount": // New Account
                AccountController ac4 = new AccountController(new AccountModel(), new AccountView());
                ac4.newMedewerkerAccount();
                break;                
            case "wijzigAccount": // Change Account
                AccountController ac2 = new AccountController(new AccountModel(), new AccountView());
                ac2.selectAccountToModify();
                break;
            case "verwijderAccount": // Delete Account
                AccountController ac3 = new AccountController(new AccountModel(), new AccountView());
                ac3.deleteAccount();
                break;
            case "wijzigGebruiker": // Change own Account
                AccountController ac5 = new AccountController(new AccountModel(loginManager), new AccountView());
                ac5.modifyOwnUsername();
                break;
            case "wijzigWachtwoord": // Change own Account
                AccountController ac6 = new AccountController(new AccountModel(loginManager), new AccountView());
                ac6.modifyOwnPassword();
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
            case "wijzigKlantZelf": // Change logged in Klant
                KlantenModel km = new KlantenModel();
                KlantModel km2 = km.getKlantById(loginManager.getAccountPojo().getKlantId());
                KlantController kc4 = new KlantController(km2, new KlantView());
                kc4.modifyKlant();
                break;
                
                //Bestellingen en Artikelen ingevoerd door Jung, voorlopig werkende versie.
                //Moet nog een scheiding tussen controller en model gemaakt worden,
                //en misschien tussen klant- en medewerkeropties.
                
                //BESTELLINGEN:
            case "Nieuwe bestelling": //nieuwe bestelling
                BestellingController bcTest1 = new BestellingController (new BestellingPojo(),
                        new BestellingView(), new BestelArtikelPojo(), new ArtikelView());
                bcTest1.newOrderInput();
                break;
            case "Bestelling wijzigen": //artikelen toevoegen aan of wijzigen in een bestelling
                BestellingController bcTest2 = new BestellingController (new BestellingPojo(),
                        new BestellingView(), new BestelArtikelPojo(), new ArtikelView());
                bcTest2.modifyOrder();
                break;
            case "Bestelling verwijderen": //artikelen verwijderen van bestelling of totale bestelling verwijderen
                BestellingController bcTest3 = new BestellingController (new BestellingPojo(),
                        new BestellingView(), new BestelArtikelPojo(), new ArtikelView());
                bcTest3.deleteOrderOptions();
                break;
                
            //ARTIKELEN:
            case "Nieuw artikel":
                ArtikelController artC1 = new ArtikelController (new ArtikelPojo (), new ArtikelView ());
                artC1.createArticle();
                break;
            case "Wijzig artikel":
                ArtikelController artC2 = new ArtikelController (new ArtikelPojo (), new ArtikelView ());
                artC2.modifyArticle();
                break;
            case "Verwijder artikel":
                ArtikelController artC3 = new ArtikelController (new ArtikelPojo (), new ArtikelView ());
                artC3.deleteArticle();
                break;
                
            // LOGOUT
            case "logout": logout(); break;
        }
    }
}
