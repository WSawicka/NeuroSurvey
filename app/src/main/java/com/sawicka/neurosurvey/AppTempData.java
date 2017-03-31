package com.sawicka.neurosurvey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.drive.DriveId;
import com.sawicka.neurosurvey.presenter.PatientPresenter;
import com.sawicka.neurosurvey.presenter.SurveyPresenter;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mloda on 28.03.17.
 */
@Getter
@Setter
public class AppTempData implements Parcelable {
    //private AuthPresenter authPresenter;
    private PatientPresenter patientPresenter;
    private SurveyPresenter surveyPresenter;
    private DriveId driveId;

    public AppTempData(){
        //this.authPresenter = new AuthPresenter();
        this.patientPresenter = new PatientPresenter();
        this.surveyPresenter = new SurveyPresenter();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeParcelable(this.authPresenter, flags);
        dest.writeParcelable(this.patientPresenter, flags);
        dest.writeParcelable(this.surveyPresenter, flags);
    }

    protected AppTempData(Parcel in) {
        //this.authPresenter = in.readParcelable(AuthPresenter.class.getClassLoader());
        if(this.patientPresenter == null) this.patientPresenter = new PatientPresenter();
        if(this.surveyPresenter == null) this.surveyPresenter = new SurveyPresenter();

        this.patientPresenter = in.readParcelable(PatientPresenter.class.getClassLoader());
        this.surveyPresenter = in.readParcelable(SurveyPresenter.class.getClassLoader());
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
