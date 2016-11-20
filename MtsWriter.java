/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author MorcCo
 */
public class MtsWriter {

    private ArrayList<MtsSum> MTS;
    private String path;
    private float Totalsum;

    public MtsWriter(ArrayList<MtsSum> MtsSumcoll, String outph) {
        super();
        this.MTS = MtsSumcoll;
        this.path = outph;
    }

    protected void Go() {
        MTS.forEach((MtsSum scoll) -> {
            Totalsum += (float) (Math.round(Float.parseFloat(scoll.getCatSum()) * 10000)) / 10000;
            WriteToFile(Totalsum);
        });
    }

    protected void WriteToFile(float Sum) {

        try (FileWriter fr = new FileWriter(path); BufferedWriter br = new BufferedWriter(fr)) {
            for (MtsSum scoll : MTS) {
                br.write(scoll.getPHaccount() + " " + scoll.getPHdivision() + " "
                        + scoll.getPHdepartment() + " " + scoll.getPHcostcenter() + " "
                        + (float) (Math.round((Float.parseFloat(scoll.getCatSum())) * 100)) / 100);
                br.flush();
                br.newLine();
            }
            br.write("Total sum = " + String.valueOf(Sum));
            br.close();

        } catch (IOException ex) {
            System.out.println("Following I/O exception occured" + ex);
        }

    }
}
