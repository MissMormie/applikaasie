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
public class MenuView {

    /**
     * Prints the menu items in the current node, if there's a @name this is used, otherwise textnode.
     * @param nodeList NodeList containing <menuItem>
     */
    public void showMenu(NodeList nodeList) {
        System.out.println("\n------------------------------------------------------------------------\n");        

        for(int i = 0; i < nodeList.getLength(); i++) {
            if(nodeList.item(i).getAttributes().getNamedItem("name") != null)
                System.out.print(nodeList.item(i).getAttributes().getNamedItem("number").getNodeValue() + " " +
                             nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue()
                             + "\n");                
            else 
                System.out.print(nodeList.item(i).getAttributes().getNamedItem("number").getNodeValue() + " " +
                             nodeList.item(i).getTextContent()
                             + "\n");
        }
    }
}
