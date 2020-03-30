package tech.minthura.caresdk.service;

public interface CovidApiCallback<T> {

    void onSuccess(T t);
    void onError(ErrorResponse errorResponse);

}
