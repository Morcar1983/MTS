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
public class MtsSum extends MTSDataCollector {

    private String catSum;

    private MtsSum(ArrayList<String> PH, String catSum) {
        super(PH);
        this.catSum = catSum;
    }

    public String getCatSum() {
        return catSum;
    }

    public void setCatSum(String catSum) {
        this.catSum = catSum;
    }

    protected static void MtsSumFactory(ArrayList<MTSDataCollector> MTScoll, ArrayList<MtsSum> MtsSumcoll) {
        int index = 0;
        for (MTSDataCollector mts : MTScoll) {
            boolean flag = true;
            if (index != 0) {
                for (int i = 0; i < index; i++) {
                    if ((mts.getPHsum() != null) && (mts.getPHdivision().equals(MtsSumcoll.get(i).getPHdivision()))
                            && ((mts.getPHdepartment().equals(MtsSumcoll.get(i).getPHdepartment())))
                            && ((mts.getPHcostcenter().equals(MtsSumcoll.get(i).getPHcostcenter())))) {
                        MtsSumcoll.get(i).setCatSum(String.valueOf((float) Math.round((float) ((Float.parseFloat(MtsSumcoll.get(i).getCatSum().replace(',', '.')))
                                + (float) (Float.parseFloat(mts.getPHsum().
                                        replace(',', '.')))) * 10000) / 10000));
                        flag = false;
                    }
                }
            }
            if ((mts.getPHsum() != null) && flag) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(mts.getPHnumber());
                temp.add(mts.getPHaccount());
                temp.add(mts.getPHdivision());
                temp.add(mts.getPHdepartment());
                temp.add(mts.getPHcostcenter());
                MtsSumcoll.add(new MtsSum(temp, mts.getPHsum().replace(',', '.')));
                index++;
            }
        }
    }
}