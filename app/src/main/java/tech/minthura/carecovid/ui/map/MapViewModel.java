package tech.minthura.carecovid.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.MMMapInfo;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;

public class MapViewModel extends ViewModel {

    private MutableLiveData<MMMapInfo> mMapInfo;
    private MutableLiveData<ErrorResponse> mError;

    public MapViewModel() {
        mMapInfo = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    void getHospitals() {
        Session.getSession().getHospitals(new CovidApiCallback<MMMapInfo>() {
            @Override
            public void onSuccess(MMMapInfo hospitals) {
                mMapInfo.setValue(hospitals);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    LiveData<MMMapInfo> getMapInfoLiveData() {
        return mMapInfo;
    }
    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }

}