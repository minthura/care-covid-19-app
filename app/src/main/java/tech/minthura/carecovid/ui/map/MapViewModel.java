package tech.minthura.carecovid.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Hospital;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;

public class MapViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Hospital>> mHospitals;
    private MutableLiveData<ErrorResponse> mError;

    public MapViewModel() {
        mHospitals = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    void getHospitals() {
        Session.getSession().getHospitals(new CovidApiCallback<ArrayList<Hospital>>() {
            @Override
            public void onSuccess(ArrayList<Hospital> hospitals) {
                mHospitals.setValue(hospitals);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    LiveData<ArrayList<Hospital>> getHospitalsLiveData() {
        return mHospitals;
    }
    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }

}