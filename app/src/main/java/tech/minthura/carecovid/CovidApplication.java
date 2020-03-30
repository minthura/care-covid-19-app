package tech.minthura.carecovid;

import android.app.Application;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.caresdk.Session;

public class CovidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MDetect.INSTANCE.init(this);
        Session.init(getString(R.string.api_base_url), getApplicationContext(), BuildConfig.VERSION_CODE);
    }
}
