package tech.minthura.caresdk.service;

import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {

    private final ApiService apiService;

    public BaseService(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("app-token", "2yfzhbfu93ycnxezr43wu85")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    ApiService getApiService() {
        return apiService;
    }

    void handleResponse(Response response, CovidApiCallback callback) {
        if(response.isSuccessful()) {
            callback.onSuccess(response.body());
        } else {
            try {
                Log.e("MMDotaErrorResponse", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (response.code()) {
                case 503:
                    callback.onError(new ErrorResponse(Error.ERROR_SERVER_MAINTENANCE));
                    break;
                default:
                    callback.onError(new ErrorResponse(Error.ERROR_SERVER));
                    break;
            }
        }
    }

    void handleFailure(Throwable error, CovidApiCallback callback) {
        error.printStackTrace();
        if (error instanceof SocketTimeoutException) {
            callback.onError(new ErrorResponse(Error.ERROR_SERVER));
        } else if (error instanceof ConnectException) {
            callback.onError(new ErrorResponse(Error.ERROR_CONNECTION));
        } else {
            callback.onError(new ErrorResponse(Error.ERROR_SERVER));
        }
    }

}
