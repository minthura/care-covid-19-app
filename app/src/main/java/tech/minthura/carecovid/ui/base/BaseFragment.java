package tech.minthura.carecovid.ui.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import tech.minthura.caresdk.Session;

public class BaseFragment extends Fragment {

    private void changeLanguage() {
        String lang = Session.getSession().getCurrentLanguage();
        if (lang.length() > 0) {
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale(lang);
            res.updateConfiguration(conf, dm);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeLanguage();
    }

    @Override
    public void onResume() {
        super.onResume();
        changeLanguage();
    }

}
