package tech.minthura.caresdk.service;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.Device;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.model.TotalStats;

interface ApiService {

    @GET("all")
    Call<TotalStats> getAllStats();

    @GET("countries")
    Call<ArrayList<Country>> getCountries();

    @GET("countries/{preferredCountry}")
    Call<Country> getPreferredCountry(@Path("preferredCountry") String preferredCountry);

    @GET("who/images/{id}")
    Call<ArrayList<String>> getWhoImages(@Path("id") int id);

    @POST("device/register")
    Call<Device> registerDevice(@Body Device device);

    @GET("post")
    Call<ArrayList<Post>> getPosts();

}
