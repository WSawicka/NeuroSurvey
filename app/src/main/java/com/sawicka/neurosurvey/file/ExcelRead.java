package com.sawicka.neurosurvey.file;

import android.os.AsyncTask;

import com.google.android.gms.drive.DriveApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by mloda on 07.04.17.
 */

public class ExcelRead extends AsyncTask<Void, Void, List<Cell[]>> {
    private DriveApi.DriveContentsResult result;

    public ExcelRead(DriveApi.DriveContentsResult result){
        this.result = result;
    }

    @Override
    protected List<Cell[]> doInBackground(Void... params) {
        List<Cell[]> rows = new ArrayList<>();
        InputStream inputStream = result.getDriveContents().getInputStream();
        try {
            Workbook wb = Workbook.getWorkbook(inputStream);
            Sheet sheet = wb.getSheet(0);
            for (int i=0;i<sheet.getRows();i++){
                rows.add(sheet.getRow(i));
            }
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
