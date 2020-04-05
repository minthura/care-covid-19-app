package tech.minthura.carecovid.support;

public interface HomeListener {

    void onPostDetails(String type, String description);
    void onFullScreenImageView(String url);
    void dismissSoftKeyboard();
}
