package com.sawicka.neurosurvey.file;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveApi;
import com.sawicka.neurosurvey.AppTempData;
import com.sawicka.neurosurvey.enums.questions.CheckQuestEnum;
import com.sawicka.neurosurvey.model.Patient;
import com.sawicka.neurosurvey.model.Survey;
import com.sawicka.neurosurvey.model.questions.CheckQuestion;
import com.sawicka.neurosurvey.model.questions.OtherAnswerQuestion;
import com.sawicka.neurosurvey.model.questions.OtherQuestion;
import com.sawicka.neurosurvey.model.questions.Question;
import com.sawicka.neurosurvey.model.questions.RadioSeekQuestion;
import com.sawicka.neurosurvey.utils.MyAlert;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by mloda on 28.03.17.
 */

public class ExcelWrite extends AsyncTask<Void, Void, Void>{
    private DriveApi.DriveContentsResult result;
    private GoogleApiClient client;
    private List<Cell[]> rows;
    private AppTempData appData;
    private Context context;
    private Resources resources;

    public ExcelWrite(DriveApi.DriveContentsResult result, GoogleApiClient client, List<Cell[]> rows,
                      AppTempData appData, Context context, Resources resources){
        this.result = result;
        this.client= client;
        this.rows = rows;
        this.appData = appData;
        this.context = context;
        this.resources = resources;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OutputStream outputStream = result.getDriveContents().getOutputStream();
        try {
            WritableWorkbook wrwb = Workbook.createWorkbook(outputStream);
            WritableSheet sheet = wrwb.createSheet("Sheet1", 0);

            int lastRow = fillPreviousData(sheet, rows) + 1;
            setPatientData(sheet, lastRow, appData.getPatientPresenter().getPatient());
            setSurveyData(sheet, lastRow, appData.getSurveyPresenter().getSurvey());
            setComments(sheet, lastRow, appData.getSurveyPresenter().getSurvey().getComments());

            wrwb.write();
            wrwb.close();
            outputStream.flush();
            outputStream.close();

            result.getDriveContents().commit(client, null);
        } catch (WriteException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int fillPreviousData(WritableSheet sheet, List<Cell[]> rows) throws WriteException {
        int lastRow = -1;
        for(Cell[] cells : rows){
            for (Cell cell : cells) {
                try{
                    double value = Double.parseDouble(cell.getContents());
                    sheet.addCell(new Number(cell.getColumn(), cell.getRow(), value));
                } catch (NumberFormatException nfex){
                    sheet.addCell(new Label(cell.getColumn(), cell.getRow(), cell.getContents()));
                }
                if (lastRow < cell.getRow()) lastRow = cell.getRow();
            }
        }
        return lastRow;
    }

    private void setPatientData(WritableSheet sheet, int lastRow, Patient patient){
        WritableCellFormat format = new WritableCellFormat(new DateFormat("dd.MM.yyyy"));
        try {
            sheet.addCell(new Label(0, lastRow, patient.getName()));
            sheet.addCell(new Number(1, lastRow, patient.getAge()));
            if(patient.getGender()!= null) {
                sheet.addCell(new Label(2, lastRow, patient.getGender().name()));
            }
            sheet.addCell(new DateTime(3, lastRow, patient.getActualDate().toDate(), format));
            if(patient.getOperationDate() != null) {
                sheet.addCell(new DateTime(4, lastRow, patient.getOperationDate().toDate(), format));
            }
            sheet.addCell(new Label(5, lastRow, patient.getOperationName()));
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    private void setSurveyData(WritableSheet sheet, int lastRow, Survey survey){
        List<Object> output = new ArrayList<>();
        for(Map.Entry<String, Question> questionEntry : survey.getQuestions().entrySet()){
            String key = questionEntry.getKey();
            Question question = questionEntry.getValue();
            if (question instanceof RadioSeekQuestion) {
                setRadioSeekQuestion(sheet, lastRow, key, question);
            } else if (question instanceof CheckQuestion) {
                setCheckQuestion(sheet, lastRow, key, question);
            } else if (question instanceof OtherQuestion || question instanceof OtherAnswerQuestion) {
                setOtherQuestion(sheet, lastRow, key, question);
            }
        }
    }

    private void setRadioSeekQuestion(WritableSheet sheet, int lastRow, String key, Question question){
        int col = getColumnNumberWith(rows.get(0), key);
        if (col >= 0) {
            try {
                sheet.addCell(new Number(col, lastRow, (Integer) question.getDataToFile()));
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } else {
            new MyAlert().showAlertDialog("Error while getting col labels!", context);
        }
    }

    private void setCheckQuestion(WritableSheet sheet, int lastRow, String key, Question question){
        //numer checkbox x pytania Y odpowiada numerowi w kolumnie Y_x
        //te których brakuje -> 0
        List<Integer> answers = (List<Integer>) question.getDataToFile();
        int optionsListId = CheckQuestEnum.valueOf(key).getOptionsArrayId();
        String[] options = resources.getStringArray(optionsListId);

        for (int i = 1; i <= options.length; i++) {
            int value = (answers.contains(i)) ? 1 : 0;
            int col = getColumnNumberWith(rows.get(0), key + "_" + i);
            if (col >= 0) {
                try {
                    sheet.addCell(new Number(col, lastRow, value));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } else {
                new MyAlert().showAlertDialog("Error while getting col labels!", context);
            }
        }
    }

    private void setOtherQuestion(WritableSheet sheet, int lastRow, String key, Question question){
        int col = getColumnNumberWith(rows.get(0), key);
        if (col >= 0) {
            try {
                sheet.addCell(new Label(col, lastRow, (String) question.getDataToFile()));
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } else {
            new MyAlert().showAlertDialog("Error while getting col labels!", context);
        }
    }

    private void setComments(WritableSheet sheet, int lastRow, String comments){
        int col = getColumnNumberWith(rows.get(0), "Comments");
        if(col >= 0) {
            try {
                sheet.addCell(new Label(col, lastRow, comments));
            } catch(WriteException ex) {
                new MyAlert().showAlertDialog("Error while getting col labels", context);
            }
        }
    }

    private int getColumnNumberWith(Cell[] row, String keyName) {
        for(Cell cell : row){
            if(cell.getContents().equals(keyName)) {
                return cell.getColumn();
            }
        }
        return -1;
    }
}
