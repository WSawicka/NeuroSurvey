package com.sawicka.neurosurvey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.drive.DriveId;
import com.sawicka.neurosurvey.presenter.PatientPresenter;
import com.sawicka.neurosurvey.presenter.SurveyPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import jxl.Cell;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 28.03.17.
 */
@Getter
@Setter
public class AppTempData implements Parcelable {
    private PatientPresenter patientPresenter;
    private SurveyPresenter surveyPresenter;
    private DriveId driveId;
    private int lastRowNum;
    private LinkedHashMap<String, List<String>> commonAnswers = new LinkedHashMap<>();
    private List<Cell[]> rows;

    public AppTempData(){
        this.patientPresenter = new PatientPresenter();
        this.surveyPresenter = new SurveyPresenter();
        this.lastRowNum = 0;
        setCommonAnswers();
    }

    private void setCommonAnswers(){
        this.commonAnswers.put("Q2b", new ArrayList<>(
                Arrays.asList("a2b", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q9_9", new ArrayList<>(
                Arrays.asList("a9_9", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q11", new ArrayList<>(
                Arrays.asList("a11", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q12", new ArrayList<>(
                Arrays.asList("a12", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q23_7", new ArrayList<>(
                Arrays.asList("a23_7", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q24_8", new ArrayList<>(
                Arrays.asList("a24_8", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q25", new ArrayList<>(
                Arrays.asList("a25", "a3", "a4", "a16", "b12")));

        this.commonAnswers.put("Q31_11", new ArrayList<>(
                Arrays.asList("a31_11", "a3", "a4", "a16", "b12")));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.patientPresenter, flags);
        dest.writeParcelable(this.surveyPresenter, flags);
        dest.writeParcelable(this.driveId, flags);
        dest.writeInt(this.lastRowNum);
        dest.writeList(this.rows);
    }

    protected AppTempData(Parcel in) {
        this.patientPresenter = in.readParcelable(PatientPresenter.class.getClassLoader());
        this.surveyPresenter = in.readParcelable(SurveyPresenter.class.getClassLoader());
        this.driveId = in.readParcelable(DriveId.class.getClassLoader());
        this.rows = in.readArrayList(ArrayList.class.getClassLoader());
        setCommonAnswers();
        this.lastRowNum = 0;
    }

    public static final Parcelable.Creator<AppTempData> CREATOR = new Parcelable.Creator<AppTempData>() {
        @Override
        public AppTempData createFromParcel(Parcel source) {
            return new AppTempData(source);
        }

        @Override
        public AppTempData[] newArray(int size) {
            return new AppTempData[size];
        }
    };
}
