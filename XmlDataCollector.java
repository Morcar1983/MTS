/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author MorcCo
 */
public class XmlDataCollector {

    private String mAttr, awodAttr;

    private XmlDataCollector(String[] Attr) {
        this.mAttr = Attr[0];
        this.awodAttr = Attr[1];
    }

    public String getmAttr() {
        return mAttr;
    }

    public String getAwodAttr() {
        return awodAttr;
    }

    protected static void XmlDataCollectorFactory(ArrayList<XmlDataCollector> List, String xmlph) {

        try {
            try {
                SAXParserFactory.newInstance().newSAXParser().parse(new File(xmlph),
                        new DefaultHandler() {
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) {
                        if (qName.equals("ps") && attributes.getValue("m") != null) {
                            List.add(new XmlDataCollector(new String[]{attributes.getValue("m"),
                                attributes.getValue("awt")}));
                        }
                    }
                });
            } catch (IOException ex) {
                System.out.println("File exception occured" + ex);
            }
        } catch (ParserConfigurationException | SAXException ex) {
            System.out.println("The following Sax exception occured " + ex);
        }
    }
}