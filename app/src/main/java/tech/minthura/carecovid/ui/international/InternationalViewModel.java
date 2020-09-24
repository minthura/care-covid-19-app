package tech.minthura.carecovid.ui.international;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.TotalStats;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;

public class InternationalViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Country>> mCountries;
    private MutableLiveData<ErrorResponse> mError;

    public InternationalViewModel() {
        mCountries = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    void getCountriesData() {
        Session.getSession().getCountries(new CovidApiCallback<ArrayList<Country>>() {
            @Override
            public void onSuccess(ArrayList<Country> countries) {
                mCountries.setValue(countries);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    LiveData<ArrayList<Country>> getCountriesLiveData() {
        return mCountries;
    }

    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }
}