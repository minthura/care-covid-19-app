package tech.minthura.carecovid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.TotalStats;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Country>> mCountries;
    private MutableLiveData<TotalStats> mTotalStats;
    private MutableLiveData<Country> mPreferredCountry;
    private MutableLiveData<ErrorResponse> mError;
    private MutableLiveData<Boolean> mFinishedResponse;
    private boolean finishedCountriesDataResponse = false;
    private boolean finishedAllStatsResponse = false;
    private boolean finishedPreferredCountryResponse = false;

    public HomeViewModel() {
        mCountries = new MutableLiveData<>();
        mPreferredCountry = new MutableLiveData<>();
        mTotalStats = new MutableLiveData<>();
        mFinishedResponse = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    void getCountriesData() {
        finishedCountriesDataResponse = false;
        Session.getSession().getCountries(new CovidApiCallback<ArrayList<Country>>() {
            @Override
            public void onSuccess(ArrayList<Country> countries) {
                mCountries.setValue(countries);
                finishedCountriesDataResponse = true;
                checkResponse();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    void getAllStats() {
        finishedAllStatsResponse = false;
        Session.getSession().getAllStats(new CovidApiCallback<TotalStats>() {
            @Override
            public void onSuccess(TotalStats totalStats) {
                mTotalStats.setValue(totalStats);
                finishedAllStatsResponse = true;
                checkResponse();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    void getPreferredCountry() {
        finishedPreferredCountryResponse = false;
        Session.getSession().getPreferredCountry(new CovidApiCallback<Country>() {
            @Override
            public void onSuccess(Country country) {
                mPreferredCountry.setValue(country);
                finishedPreferredCountryResponse = true;
                checkResponse();
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    private void checkResponse() {
        if (finishedAllStatsResponse && finishedCountriesDataResponse && finishedPreferredCountryResponse){
            mFinishedResponse.setValue(true);
        } else {
            mFinishedResponse.setValue(false);
        }
    }

    LiveData<ArrayList<Country>> getCountriesLiveData() {
        return mCountries;
    }

    LiveData<TotalStats> getTotalStatsLiveData() {
        return mTotalStats;
    }

    LiveData<Boolean> getFinishedResponseLiveData() {
        return mFinishedResponse;
    }

    LiveData<Country> getPreferredCountryLiveData() {
        return mPreferredCountry;
    }

    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }
}