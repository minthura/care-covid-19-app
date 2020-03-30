package tech.minthura.carecovid.ui.postdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import tech.minthura.caresdk.service.ErrorResponse;

public class PostDetailViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mImageUrls;
    private MutableLiveData<ErrorResponse> mError;

    public PostDetailViewModel() {
        mImageUrls = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    public LiveData<ArrayList<String>> getImageUrlsLiveData() {
        return mImageUrls;
    }

    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }
}