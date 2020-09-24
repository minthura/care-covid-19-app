package tech.minthura.carecovid.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import me.myatminsoe.mdetect.MDetect;
import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.carecovid.support.NumberFormatter;
import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.TotalStats;
import tech.minthura.caresdk.service.ErrorResponse;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Timer mTimer;
    private HomeListener mHomeListener;
    private SmoothProgressBar progressbar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            mHomeListener = (HomeListener)context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtTotalCases = view.findViewById(R.id.txt_total_cases);
        TextView txtTotalDeaths = view.findViewById(R.id.txt_total_deaths);
        TextView txtTotalRecovered = view.findViewById(R.id.txt_total_recovered);
        TextView txtTotalTerritories = view.findViewById(R.id.txt_total_territories);
        TextView txtPreferredCountry = view.findViewById(R.id.txt_preferred_country);
        TextView txtPreferredCountryCases = view.findViewById(R.id.txt_mm_cases);
        TextView txtPreferredCountryDeaths = view.findViewById(R.id.txt_mm_deaths);
        TextView txtPreferredCountryRecovered = view.findViewById(R.id.txt_mm_recovered);
        TextView txtNewCases = view.findViewById(R.id.txt_new_cases);
        TextView txtNewDeaths = view.findViewById(R.id.txt_new_deaths);
        TextView txtNewRecovered = view.findViewById(R.id.txt_new_recovered);
        TextView txtInfoDate = view.findViewById(R.id.txt_info_date);
        ImageView imgPreferredCountryFlag = view.findViewById(R.id.img_preferred_country_flag);
        view.findViewById(R.id.btn_view_mohs).setOnClickListener(view1 -> DialogUtils.openFacebookPage(getContext()));
        progressbar = view.findViewById(R.id.progressbar);
        homeViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Country>>() {
            @Override
            public void onChanged(ArrayList<Country> countries) {
                txtTotalTerritories.setText(NumberFormatter.INSTANCE.formatToUnicode(countries.size()));
            }
        });
        homeViewModel.getTotalStatsLiveData().observe(getViewLifecycleOwner(), new Observer<TotalStats>() {
            @Override
            public void onChanged(TotalStats totalStats) {
                txtTotalCases.setText(NumberFormatter.INSTANCE.formatToUnicode(totalStats.getCases()));
                txtTotalDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(totalStats.getDeaths()));
                txtTotalRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(totalStats.getRecovered()));
            }
        });
        homeViewModel.getPreferredCountryLiveData().observe(getViewLifecycleOwner(), new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                txtPreferredCountryCases.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getCases()));
                txtPreferredCountryDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getDeaths()));
                txtPreferredCountryRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getRecovered()));
                txtPreferredCountry.setText(country.getCountry());
                txtNewCases.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getNewconfirm()));
                txtNewDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getNewdeaths()));
                txtNewRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getNewrecovered()));
                txtInfoDate.setText(MDetect.INSTANCE.getText(String.format(getString(R.string.app_information_received_label), country.getInfodate())));
                Picasso.get().load(country.getCountryInfo().getFlag()).into(imgPreferredCountryFlag);
            }
        });
        homeViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
//                lottieAnimationView.setVisibility(View.GONE);
            }
        });
        homeViewModel.getFinishedResponseLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    progressbar.progressiveStop();
                }
            }
        });
        txtTotalCases.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalCases()));
        txtTotalDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalDeaths()));
        txtTotalRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalRecovered()));
        txtTotalTerritories.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalTerritories()));
        txtPreferredCountryCases.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryCases()));
        txtPreferredCountryDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryDeaths()));
        txtPreferredCountryRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryRecovered()));
        txtNewCases.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getNewConfirmCases()));
        txtNewDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getNewConfirmDeaths()));
        txtNewRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getNewConfirmRecovered()));
        txtInfoDate.setText(MDetect.INSTANCE.getText(String.format(getString(R.string.app_information_received_label), Session.getSession().getInfoDate())));
        txtPreferredCountry.setText(Session.getSession().getPreferredCountry());
        Picasso.get().load(Session.getSession().getPreferredCountryFlagUrl()).into(imgPreferredCountryFlag);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        delayedRun();
        progressbar.progressiveStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTimer != null){
            mTimer.cancel();
        }
    }

    private void delayedRun() {
        //Set the schedule function
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                homeViewModel.getCountriesData();
                homeViewModel.getAllStats();
                homeViewModel.getPreferredCountry();
            }
        }, 0, 60000);
    }
}
