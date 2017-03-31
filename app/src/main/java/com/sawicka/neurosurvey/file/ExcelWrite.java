package com.sawicka.neurosurvey.file;

import com.google.android.gms.drive.DriveApi;

import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by mloda on 28.03.17.
 */

public class ExcelWrite {
    public void getFileContent(DriveApi.DriveContentsResult result){
        InputStream inputStream = result.getDriveContents().getInputStream();
        try{
            Workbook wbRead = Workbook.getWorkbook(inputStream);
            Sheet sheet = wbRead.getSheet(0);
            Cell[] titles = sheet.getRow(0);
            wbRead.close();
        }
        catch (BiffException | IOException e) {
            e.printStackTrace();
        }
    }
}
