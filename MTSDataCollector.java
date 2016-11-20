/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author MorcCo
 */
public class MTSDataCollector {

    protected String PHnumber, PHaccount, PHdivision, PHdepartment, PHcostcenter, PHsum;

    protected MTSDataCollector(ArrayList<String> PH) {
        super();
        this.PHnumber = PH.get(0);
        this.PHaccount = PH.get(1);
        this.PHdivision = PH.get(2);
        this.PHdepartment = PH.get(3);
        this.PHcostcenter = PH.get(4);
    }

    public String getPHsum() {
        return PHsum;
    }

    public void setPHsum(String PHsum) {
        this.PHsum = PHsum;
    }

    public String getPHaccount() {
        return PHaccount;
    }

    public String getPHnumber() {
        return PHnumber;
    }

    public String getPHdivision() {
        return PHdivision;
    }

    public String getPHdepartment() {
        return PHdepartment;
    }

    public String getPHcostcenter() {
        return PHcostcenter;
    }

    protected static void MTSDataCollectorFactory(ArrayList<MTSDataCollector> List, String numph) {
        try (FileReader fr = new FileReader(numph); BufferedReader br = new BufferedReader(fr)) {
            String readMTS;
            ArrayList<String> temp = new ArrayList<>();
            while ((readMTS = br.readLine()) != null) {
                int index = 0;
                while (readMTS.indexOf("\t", index) != -1) {
                    temp.add(readMTS.substring(index, (readMTS.indexOf("\t", index))));
                    index +=(readMTS.substring(index, (readMTS.indexOf("\t", index)))).length() +1;
                }
                temp.add(readMTS.substring(index, readMTS.length()));
                List.add(new MTSDataCollector(temp));
                temp.clear();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Check file name " + ex);
        } catch (IOException ex) {
            System.out.println("Following I/O error occured" + ex);
        }
    }
}