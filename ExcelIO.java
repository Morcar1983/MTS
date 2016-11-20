/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtsparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author MorcCo
 */
public class ExcelIO {

    private int index;
    private boolean flag1, flag2;
    private Calendar calen;

    public void parse(ArrayList<MTSDataCollector> MTS, String xls) {
        File wkb = new File (xls);
        calen = Calendar.getInstance();
        calen.set(Calendar.MONTH, calen.get(Calendar.MONTH)-1);
        try (Workbook wb = WorkbookFactory.create(wkb)) {
            wb.getSheetAt(0).forEach((Row t) -> {
                for (int temp = 1; temp < t.getLastCellNum(); temp++) {
                    if (t.getCell(temp) != null) {
                        if ((index == 0) && (t.getCell(temp).getCellTypeEnum() == CellType.STRING
                                && !t.getCell(temp).getStringCellValue().equals(""))
                                && t.getCell(temp).getStringCellValue().equals("Лимит")) {
                            index = t.getCell(temp).getColumnIndex();
                            if (t.getCell(index - 1).getStringCellValue().equals(calen.
                                    getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, new Locale("ru")))) {
                                System.out.println("will not do anything");
                                flag1 = true;
                            }
                            continue;
                        }
                        if (flag1 == false) {
                            if ((index != 0) && (temp == (index + 1))) {
                                Cell celltmp;
                                for (int shifttmp = 4; shifttmp > 0; shifttmp--) {
                                    if (t.getCell(index + shifttmp - 1) != null) {
                                        switch (t.getCell(index + shifttmp - 1).getCellTypeEnum()) {
                                            case STRING:
                                                celltmp = t.createCell(index + shifttmp);
                                                celltmp.setCellValue(t.getCell(index + shifttmp - 1).getStringCellValue());
                                                celltmp.setCellStyle(t.getCell(index + shifttmp - 1).getCellStyle());
                                                break;
                                            case NUMERIC:
                                                celltmp = t.createCell(index + shifttmp);
                                                celltmp.setCellValue(t.getCell(index + shifttmp - 1).getNumericCellValue());
                                                celltmp.setCellStyle(t.getCell(index + shifttmp - 1).getCellStyle());
                                                break;
                                            default:
                                                celltmp = t.createCell(index + shifttmp);
                                                celltmp.setCellValue("");
                                                celltmp.setCellStyle(t.getCell(index + shifttmp - 1).getCellStyle());
                                                break;
                                        }
                                    }
                                }
                                flag2 = false;
                                MTS.forEach((MTSDataCollector mts) -> {
                                    if ((t.getCell(0).getCellTypeEnum() == CellType.NUMERIC) && (mts.getPHsum() != null)) {
                                        if (mts.getPHnumber().equals((String.valueOf(t.getCell(0).getNumericCellValue())).
                                                replace(".", "").substring(0, 11).replace("E", "0"))) {
                                            t.getCell(index).setCellValue(Double.parseDouble(mts.getPHsum().substring(0, mts.getPHsum().length() - 2).replace(',', '.')));
                                            t.getCell(index).setCellStyle(t.getCell(index - 1).getCellStyle());
                                            flag2 = true;
                                        }
                                    }
                                });
                                if ((flag2 == false) && (t.getCell(index) != null)) {
                                    t.getCell(index).setCellValue("");
                                    t.getCell(index).setCellStyle(t.getCell(index - 1).getCellStyle());
                                }
                            }
                        }
                    }
                }
            });
            if (flag1 == true) {
                return;
            }
            wb.getSheetAt(0).setAutoFilter(CellRangeAddress.valueOf("A2:"
                    + wb.getSheetAt(0).getRow(1).getCell(index + 4).getAddress()));
            for (int i = 1; i < 5; i++) {
                wb.getSheetAt(0).autoSizeColumn(index + i);
            }
            wb.getSheetAt(0).getRow(1).getCell(index).setCellValue(calen.
                    getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, new Locale("ru")));
            try (FileOutputStream out = new FileOutputStream(wkb,true)) {
                wb.write(out);
            } catch (FileNotFoundException th) {
                System.out.println("File was not found");
            } catch (IOException io) {
                System.out.println("Error during saving");
            }
        } catch (EncryptedDocumentException ee) {
            System.out.println("Document is password protected");
        } catch (IOException io) {
            System.out.println("Reading Error");
        } catch (InvalidFormatException inf) {
            System.out.println("Invalid file format");
        } catch (Throwable th) {
            System.out.println("Throwable" + th);
        }
    }
}