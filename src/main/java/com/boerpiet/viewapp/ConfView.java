/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import org.w3c.dom.NodeList;

/**
 *
 * @author Sonja
 */
public class ConfView {
   
    public void showDivider() {
        System.out.println("\n------------------------------------------------------------------------\n");        
    }

    public void showPickDatabase(NodeList nodeList) {
        showDivider();
        System.out.println("LET OP! Na het wijzigen van de configuratie wordt "
                         + "je uitgelogd en moet je opnieuw inloggen.");
        System.out.println("Kies de database die je wil gebruiken. Druk op N om terug te gaan.\n");
        showNodes(nodeList);
    }
    
    private void showNodes(NodeList nodeList) {
        for(int i = 0; i < nodeList.getLength(); i++) {
            System.out.print((i+1) + " " + nodeList.item(i).getAttributes().getNamedItem("type").getNodeValue());                
            if (nodeList.item(i).getAttributes().getNamedItem("default").getNodeValue().equals("true"))
                System.out.print(" - nu in gebruik.");
            System.out.println(""); 
        }
    }

    public void showModifyDatabaseFailed() {
        showDivider();
        System.out.println("Er ging iets fout bij het instellen van de nieuwe database. "
                + "Probeer het nogmaals, blijft het probleem zich voordoen neem dan contact op met IT.");
    }

    public void showInvalidInput() {
        showDivider();
        System.out.println("Vul een juist nummer in.");
    }

    public void showDatabaseChanged() {
        showDivider();
        System.out.println("De database is gewijzigd, je wordt nu uitgelogd.");
    }

    public void showPickConnectionPool(NodeList nodeList) {
        showDivider();
        System.out.println("LET OP! Na het wijzigen van de connectionpool wordt "
                         + "de applicatie afgesloten en moet je deze opnieuw opstarten.");
        System.out.println("Kies de connectionpool die je wil gebruiken. Druk op N om terug te gaan.\n");
        showNodes(nodeList);    
    }

    public void showModifyConnectionPoolFailed() {
        showDivider();
        System.out.println("Er ging iets fout bij het instellen van de nieuwe connection pool. "
                + "Probeer het nogmaals, blijft het probleem zich voordoen neem dan contact op met IT.");
    }

    public void showConnectionPoolChanged() {
        showDivider();
        System.out.println("De connectionpool is gewijzigd. Het programma wordt nu beeindigd.");
    }
}
