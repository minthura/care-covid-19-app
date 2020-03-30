package tech.minthura.carecovid;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.caresdk.Session;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Session.getSession().isFirstLaunch()){
            if(MDetect.INSTANCE.isUnicode()){
                Session.getSession().updateLanguage("my");
            } else {
                Session.getSession().updateLanguage("zg");
            }
            changeLanguage(Session.getSession().getCurrentLanguage());
        } else {
            changeLanguage(Session.getSession().getCurrentLanguage());
        }
    }

    public void changeLanguage(String lang) {
        if (lang.length() > 0) {
            findViewById(android.R.id.content).invalidate();
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale(lang);
            res.updateConfiguration(conf, dm);
            Session.getSession().updateLanguage(lang);
        }
    }

}
