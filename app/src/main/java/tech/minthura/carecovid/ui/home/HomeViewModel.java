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

    public HomeViewModel() {
        mCountries = new MutableLiveData<>();
        mPreferredCountry = new MutableLiveData<>();
        mTotalStats = new MutableLiveData<>();
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

    void getAllStats() {
        Session.getSession().getAllStats(new CovidApiCallback<TotalStats>() {
            @Override
            public void onSuccess(TotalStats totalStats) {
                mTotalStats.setValue(totalStats);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    void getPreferredCountry() {
        Session.getSession().getPreferredCountry(new CovidApiCallback<Country>() {
            @Override
            public void onSuccess(Country country) {
                mPreferredCountry.setValue(country);
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

    LiveData<TotalStats> getTotalStatsLiveData() {
        return mTotalStats;
    }

    LiveData<Country> getPreferredCountryLiveData() {
        return mPreferredCountry;
    }

    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }
}