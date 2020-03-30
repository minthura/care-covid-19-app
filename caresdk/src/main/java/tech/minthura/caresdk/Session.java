package tech.minthura.caresdk;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.Device;
import tech.minthura.caresdk.model.NotificationMessageEvent;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.model.TotalStats;
import tech.minthura.caresdk.service.BaseService;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;
import tech.minthura.caresdk.service.InformationService;

import static android.content.Context.MODE_PRIVATE;

public class Session {

    private static final String MM_DOTA_PREFERENCE = "COVID_PREFERENCE";
    private static Session sSession;
    private Context mContext;
    private InformationService informationService = new InformationService();
    private int mTotalCases = 0;
    private int mTotalDeaths = 0;
    private int mTotalRecovered = 0;
    private int mTotalTerritories = 0;
    private int mPreferredCountryCases = 0;
    private int mPreferredCountryDeaths = 0;
    private int mPreferredCountryRecovered = 0;
    private int mAppVersionCode;
    private State state = State.CLOSED;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State { OPEN, CLOSED }



    private Session(String baseUrl, Context applicationContext, int appVersionCode) {
        BaseService baseService = new BaseService(baseUrl);
        informationService.setBaseService(baseService);
        mContext = applicationContext;
        mAppVersionCode = appVersionCode;
    }

    public static void init(String baseUrl, Context applicationContext, int appVersionCode) {
        sSession = new Session(baseUrl, applicationContext, appVersionCode);
    }

    public static Session getSession() {
        return sSession;
    }

    public String getCurrentLanguage() {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        return prefs.getString("lang", "en");
    }

    public void updateLanguage(String lang) {
        SharedPreferences.Editor editor =  mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString("lang", lang);
        editor.apply();
    }

    public boolean isFirstLaunch() {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        boolean firstLaunch = prefs.getBoolean("firstLaunch", true);
        prefs.edit().putBoolean("firstLaunch", false).apply();
        return firstLaunch;
    }

    public void getCountries(final CovidApiCallback<ArrayList<Country>> callback) {
        informationService.getCountries(new CovidApiCallback<ArrayList<Country>>() {
            @Override
            public void onSuccess(ArrayList<Country> countries) {
                mTotalTerritories = countries.size();
                callback.onSuccess(countries);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                callback.onError(errorResponse);
            }
        });
    }

    public void getAllStats(final CovidApiCallback<TotalStats> callback) {
        informationService.getAllStats(new CovidApiCallback<TotalStats>() {
            @Override
            public void onSuccess(TotalStats totalStats) {
                mTotalCases = totalStats.getCases();
                mTotalDeaths = totalStats.getDeaths();
                mTotalRecovered = totalStats.getRecovered();
                callback.onSuccess(totalStats);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                callback.onError(errorResponse);
            }
        });
    }

    public void getWhoImages(int id, final CovidApiCallback<ArrayList<String>> callback){
        informationService.getWhoImages(id, callback);
    }

    public void getPosts(final CovidApiCallback<ArrayList<Post>> callback){
        informationService.getPosts(callback);
    }

    public void registerDevice(String fcmToken){
        informationService.registerDevice(fcmToken, Build.MANUFACTURER, Build.MODEL, mAppVersionCode, new CovidApiCallback<Device>() {
            @Override
            public void onSuccess(Device device) {
                Log.i("PushId", "Registered");
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Log.e("PushId", errorResponse.toString());
            }
        });
    }

    public void getPreferredCountry(final CovidApiCallback<Country> callback) {
        informationService.getPreferredCountry(getSavedPreferredCountry(), new CovidApiCallback<Country>() {
            @Override
            public void onSuccess(Country country) {
                mPreferredCountryCases = country.getCases();
                mPreferredCountryDeaths = country.getDeaths();
                mPreferredCountryRecovered = country.getRecovered();
                savePreferredCountry(country.getCountry());
                savePreferredCountryFlag(country.getCountryInfo().getFlag());
                callback.onSuccess(country);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                callback.onError(errorResponse);
            }
        });
    }

    public void savePreferredCountry(String preferredCountry) {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        prefs.edit().putString("preferredCountry", preferredCountry).apply();
    }

    private void savePreferredCountryFlag(String flag) {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        prefs.edit().putString("preferredCountryFlag", flag).apply();
    }

    private String getSavedPreferredCountry() {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        return prefs.getString("preferredCountry", "Myanmar");
    }

    public void saveLastNotification(String title, String body) {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("notificationTitle", title);
        editor.putString("notificationBody", body);
        editor.putBoolean("hasNotification", true);
        editor.apply();
    }

    public void postLastNotification() {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        boolean hasNotification = prefs.getBoolean("hasNotification", false);
        if (hasNotification){
            String title = prefs.getString("notificationTitle", "CareCovid19");
            String body = prefs.getString("notificationBody", "Thank you for using this app.");
            EventBus.getDefault().post(new NotificationMessageEvent(title, body));
        }
        editor.putBoolean("hasNotification", false);
        editor.apply();
    }

    private String getSavedPreferredCountryFlag() {
        SharedPreferences prefs = mContext.getSharedPreferences(MM_DOTA_PREFERENCE, MODE_PRIVATE);
        return prefs.getString("preferredCountryFlag", "https://raw.githubusercontent.com/NovelCOVID/API/master/assets/flags/mm.png");
    }

    public int getTotalCases() {
        return mTotalCases;
    }

    public int getTotalDeaths() {
        return mTotalDeaths;
    }

    public int getTotalRecovered() {
        return mTotalRecovered;
    }

    public int getPreferredCountryCases() {
        return mPreferredCountryCases;
    }

    public int getPreferredCountryDeaths() {
        return mPreferredCountryDeaths;
    }

    public int getPreferredCountryRecovered() {
        return mPreferredCountryRecovered;
    }

    public int getTotalTerritories() {
        return mTotalTerritories;
    }

    public String getPreferredCountry() {
        return getSavedPreferredCountry();
    }

    public String getPreferredCountryFlagUrl() {
        return getSavedPreferredCountryFlag();
    }
}
