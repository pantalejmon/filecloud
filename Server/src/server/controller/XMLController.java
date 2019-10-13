/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.MainServer;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class XMLController {

    public static void addElement(Document XMLdoc, String elementName, String elementValue) {

        Element newElement = XMLdoc.createElement(elementName);
        XMLdoc.getFirstChild().appendChild(newElement);

        newElement.appendChild(XMLdoc.createTextNode(elementValue));
        
        System.out.println("Server: XMLController.addElement(): " + elementName);

    }

    public static Document createXML()
            throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("user_config");
        doc.appendChild(rootElement);
        
        System.out.println("Server: XMLController.createXML()");

        return doc;
    }

    public static void saveXMLToFile(Document XMLdoc, String login)
            throws TransformerConfigurationException,
            TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(XMLdoc);
        StreamResult result = new StreamResult(
                new File(
                        MainServer.cloudPath
                        + login
                        + "\\config.xml"));
        
        transformer.transform(source, result);
        
        System.out.println("Server: XMLController.saveXMLToFile(): " + login);
    }

    
    
}
