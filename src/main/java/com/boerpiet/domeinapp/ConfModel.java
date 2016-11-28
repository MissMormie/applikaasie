/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Sonja
 */
public class ConfModel {
    private final Logger logger = LoggerFactory.getLogger(ConfModel.class);
    Document doc;
    private final String xmlFile = "xml/config.xml";
    
    public ConfModel() {
        readXML();
    }
    
    private boolean writeXML() {
        Transformer tr;
        try {
            tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(doc), 
                         new StreamResult(new FileOutputStream(xmlFile)));
            return true;
        } catch (Exception ex) {
            logger.debug("unable to write config.xml" + ex);
        }
        return false;
    }

    public NodeList getDatabaseNodes() {
        return getNodes("//databases/database");
    }
    
    public NodeList getConnectionPoolNodes() {
        return getNodes("//connections/connection");
    }
    
    /**
     * 
     * @param node for example: "//databases/database"
     * @return 
     */
    private NodeList getNodes(String node) {
        if(doc == null) 
            if(!readXML())
                return null;

        String xPathQuery = node;
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile(xPathQuery);
            Object exprResult = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) exprResult;
            return nodeList;
        } catch (Exception excep) {
            return null;
        }
    }
    
    
    
    private boolean readXML() {
        try {
            // Read in XML file and normalize it.
            File xml = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.info("Failed to read xml/config.xml");
            return false;
        } 
    }
    
    public boolean setDefaultConnection(int itemNr) {
        if(doc == null) 
            if(!readXML())
                return false;
        if(!changeDefault(itemNr, "connections"))
            return false;
        return writeXML();
    }
    
    public boolean setDefaultDatabase(int itemNr) {
        if(doc == null) 
            if(!readXML())
                return false;
        if(!changeDefault(itemNr, "databases"))
            return false;
        boolean success = writeXML();       
        return success;
    }
    
    // itemNr is the number of the <connection> node in the xml file (start 0)
    // Better way would be check element num exists, 
    // change current default true to false, and set new default to true
    private boolean changeDefault(int itemNr, String parentTagName) {
        
        boolean success = false;
        Node connections = doc.getElementsByTagName(parentTagName).item(0);
        NodeList list = connections.getChildNodes();

        int elements = 0;
        
        
        // Must be a better way, but can't find it. So first count the non-text elements
        int j = 0;
        for(int i = 0;  i < list.getLength(); i++) {
            if(list.item(i).getNodeType() == Node.ELEMENT_NODE) {
                j++;
            }
        }
        if (j < itemNr)
            return false;

        
        for(int i = 0;  i < list.getLength(); i++) {
            // because we don't want the text nodes. 
            if(list.item(i).getNodeType() == Node.ELEMENT_NODE) {

                NamedNodeMap attr = list.item(i).getAttributes();
                Node nodeAttr = attr.getNamedItem("default");
                if(elements == itemNr) {
                    nodeAttr.setTextContent("true");
                    success = true;
                }
                else
                    nodeAttr.setTextContent("false");
                elements++;
            }
        }
        return success;
    } 
}
