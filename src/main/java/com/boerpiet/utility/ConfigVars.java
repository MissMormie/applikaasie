/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.utility;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public class ConfigVars {
    // TODO: add proper logging and exception handling

    // Database variables.
    private static String dbType;
    private static String dbLocation; 
    private static String dbUsername;
    private static String dbPassword;
    private static String driver;
    private static String connectionType;
    
    
    private final Logger logger = LoggerFactory.getLogger(ConfigVars.class);

    public static void reset() {
        new ConfigVars();
    }    
    
    public static String getDbType() {
        if(dbType == null)
            new ConfigVars();
        return dbType;
    }

    public static String getDbLocation() {
        if(dbLocation == null)
            new ConfigVars();        
        return dbLocation;
    }

    public static String getDbUsername() {
        if(dbUsername == null)
            new ConfigVars();
        return dbUsername;
    }

    public static String getDbPassword() {
        if (dbPassword == null)
            new ConfigVars();
        return dbPassword;
    }

    public static String getDriver() {
        if(driver == null)
            new ConfigVars();
        return driver;
    }
    
    public static String getConnectionType() {
        if(connectionType == null)
            new ConfigVars();
        return connectionType;
    }

    private ConfigVars() {
        Document doc = readXML();
        setDatabase(doc, "");
        setConnection(doc, "");
    }
    
    private Document readXML() {
        try {
            // Read in XML file and normalize it.
            File xmlFile = new File("xml/config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.info("Failed to read xml/config.xml");
            System.out.println("Config.xml bestand kon niet gevonden worden. Neem contact op met de admin");
            System.exit(0);
        } 
        return null;
    }
    
    
    public boolean setDatabase(String type) {
        Document doc = readXML();
        return setDatabase(doc, type);
    }

    public boolean setConnection(String type) {
        Document doc = readXML();
        return setConnection(doc, type);
    }
    
    
    private boolean setConnection(Document doc, String type) {
        String expression;
        if(type.equals("")) 
            expression = "/root/connections/connection[@default='true']";
        else
            expression ="/root/connections/connection[@type='" + type + "'";
        XPath xpath = XPathFactory.newInstance().newXPath(); 
        
        try {
            // Get the Nodelist that adheres to the expression
            XPathExpression expr = xpath.compile(expression);
            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            Element resultNode = (Element) result.item(0);
            
            // Check if there is a node with the selected number is found.
            if(!resultNode.hasChildNodes())
                throw new Exception("No databases defined in config file");

            // Get information from nodelist.
            connectionType = resultNode.getAttribute("type");

            
        } catch (XPathExpressionException ex) {
            System.out.println("Unable to read config file. Program ends. ");
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    private boolean setDatabase(Document doc, String type) {
        String expression;
        if(type.equals("")) 
            expression = "/root/databases/database[@default='true']";
        else
            expression ="/root/databases/database[@type='" + type + "'";
        XPath xpath = XPathFactory.newInstance().newXPath(); 
        
        try {
            // Get the Nodelist that adheres to the expression
            XPathExpression expr = xpath.compile(expression);
            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
            dbType = result.item(0).getAttributes().getNamedItem("type").getNodeValue();

            Element resultNode = (Element) result.item(0);            
            // Check if there is a node with the selected number is found.
            if(!resultNode.hasChildNodes())
                throw new Exception("No databases defined in config file");

            // Get information from nodelist.
            dbLocation = resultNode.getElementsByTagName("location").item(0).getTextContent();
            dbUsername = resultNode.getElementsByTagName("username").item(0).getTextContent();
            dbPassword = resultNode.getElementsByTagName("password").item(0).getTextContent();
            driver = resultNode.getElementsByTagName("driver").item(0).getTextContent();
            
        } catch (XPathExpressionException ex) {
            System.out.println("Unable to read config file. Program ends. ");
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

}
