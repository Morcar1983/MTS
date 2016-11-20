/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MorcCo
 */
public class GuiTable {

    private JFrame myFR;
    private JTable myJT;

    public void go(ArrayList<MtsSum> list) {
        java.awt.EventQueue.invokeLater(() -> {
            cr(list);
        });
    }

    private void cr(ArrayList<MtsSum> ls) {
        myFR = new JFrame("GuiTable");
        myFR.setLayout(new GridLayout(1, 1));
        myJT = new JTable(new AbstractTableModel() {

            @Override
            public int getRowCount() {
                return ls.size();
            }

            @Override
            public int getColumnCount() {
                return 5;
            }
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return ls.get(rowIndex).getPHaccount();
                    case 1:
                        return ls.get(rowIndex).getPHdivision();
                    case 2:
                        return ls.get(rowIndex).getPHdepartment();
                    case 3:
                        return ls.get(rowIndex).getPHcostcenter();
                    case 4:
                        return ls.get(rowIndex).getCatSum();
                    default:
                        return 0;
                }
            }
            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Account";
                    case 1:
                        return "Division";
                    case 2:
                        return "Department";
                    case 3:
                        return "CostCenter";
                    case 4:
                        return "Sum";
                    default:
                        return "0";
                }
            }
        }
        );
        myJT.setAutoCreateRowSorter(true);
        myFR.add(myJT);
        //myJT.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(myJT);

        myFR.add(scrollPane);

        myFR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        myFR.setBounds(
//                10, 10, 10, 10);
        myFR.pack();

        myFR.setVisible(
                true);
    }
}
