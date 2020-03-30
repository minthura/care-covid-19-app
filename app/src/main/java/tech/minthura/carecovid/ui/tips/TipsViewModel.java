package tech.minthura.carecovid.ui.tips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.ErrorResponse;

public class TipsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Post>> mPosts;
    private MutableLiveData<ErrorResponse> mError;

    public TipsViewModel() {
        mPosts = new MutableLiveData<>();
        mError = new MutableLiveData<>();
    }

    void getPosts() {
        Session.getSession().getPosts(new CovidApiCallback<ArrayList<Post>>() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                mPosts.setValue(posts);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                mError.setValue(errorResponse);
            }
        });
    }

    LiveData<ArrayList<Post>> getPostsLiveData() {
        return mPosts;
    }
    LiveData<ErrorResponse> getErrorLiveData() {
        return mError;
    }

}