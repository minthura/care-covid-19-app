package tech.minthura.caresdk.service;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.Device;
import tech.minthura.caresdk.model.Hospital;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.model.TotalStats;

public class InformationService {

    private BaseService mBaseService;

    public void setBaseService(BaseService mBaseService) {
        this.mBaseService = mBaseService;
    }

    public void getCountries(final CovidApiCallback<ArrayList<Country>> callback) {
        Call<ArrayList<Country>> call = mBaseService.getApiService().getCountries();
        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void getAllStats(final CovidApiCallback<TotalStats> callback) {
        Call<TotalStats> call = mBaseService.getApiService().getAllStats();
        call.enqueue(new Callback<TotalStats>() {
            @Override
            public void onResponse(Call<TotalStats> call, Response<TotalStats> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<TotalStats> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void getPreferredCountry(String preferredCountry, final CovidApiCallback<Country> callback) {
        Call<Country> call = mBaseService.getApiService().getPreferredCountry(preferredCountry);
        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void getWhoImages(int id, final CovidApiCallback<ArrayList<String>> callback) {
        Call<ArrayList<String>> call = mBaseService.getApiService().getWhoImages(id);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void registerDevice(String fcmToken, String manufacturer, String model, int appVersionCode, final CovidApiCallback<Device> callback) {
        Device device = new Device();
        device.setFcmToken(fcmToken);
        device.setDeviceType("Android");
        device.setAppVersionCode(appVersionCode);
        device.setManufacturer(manufacturer);
        device.setModel(model);
        Call<Device> call = mBaseService.getApiService().registerDevice(device);
        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void getPosts(final CovidApiCallback<ArrayList<Post>> callback){
        Call<ArrayList<Post>> call = mBaseService.getApiService().getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

    public void getHospitals(final CovidApiCallback<ArrayList<Hospital>> callback){
        Call<ArrayList<Hospital>> call = mBaseService.getApiService().getHospitals();
        call.enqueue(new Callback<ArrayList<Hospital>>() {
            @Override
            public void onResponse(Call<ArrayList<Hospital>> call, Response<ArrayList<Hospital>> response) {
                mBaseService.handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<ArrayList<Hospital>> call, Throwable t) {
                mBaseService.handleFailure(t, callback);
            }
        });
    }

}
