/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.util.ArrayList;

/**
 *
 * @author MorcCo
 */
public class MTSParser {

    private ArrayList<MTSDataCollector> MTScoll;
    private ArrayList<XmlDataCollector> XMLcoll;
    private ArrayList<MtsSum> MtsSumcoll;

    public void start(String xmlph, String numph, String outph, String xls) {
        MTScoll = new ArrayList<>();
        XMLcoll = new ArrayList<>();
        MtsSumcoll = new ArrayList<>();
        MTSDataCollector.MTSDataCollectorFactory(MTScoll, numph); // creates Array of numbers
        XmlDataCollector.XmlDataCollectorFactory(XMLcoll, xmlph); // creates Array of sums
        // Compares two Arrays and adds Sums to MTS
        XMLcoll.stream().forEach((xml) -> {
            MTScoll.stream().filter((mts) -> 
                    (xml.getmAttr().equals(mts.getPHnumber())))
                    .forEach((mts) -> {mts.setPHsum(xml.getAwodAttr());
            });
        }); 
        MtsSum.MtsSumFactory(MTScoll, MtsSumcoll); // Creates Array of Categories Sums
        new MtsWriter(MtsSumcoll, outph).Go(); // Writes result to txt file
        new GuiTable().go(MtsSumcoll); // Outputs result to table
        new ExcelIO().parse(MTScoll, xls); // Outputs sums to Excel
        //System.exit(0);
    }
}