package tech.minthura.carecovid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.adapter.CountriesRecyclerViewAdapter;
import tech.minthura.carecovid.support.NumberFormatter;
import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.model.TotalStats;
import tech.minthura.caresdk.service.ErrorResponse;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CountriesRecyclerViewAdapter mCountriesRecyclerViewAdapter;
    private Timer mTimer;

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
        TextView txtError = view.findViewById(R.id.txt_error);
        ImageView imgPreferredCountryFlag = view.findViewById(R.id.img_preferred_country_flag);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.loading_lottie_view);
        RecyclerView recyclerViewCountries = view.findViewById(R.id.recyclerViewCountries);
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(getContext()));
        mCountriesRecyclerViewAdapter = new CountriesRecyclerViewAdapter(getContext(), new ArrayList<>());
        recyclerViewCountries.setAdapter(mCountriesRecyclerViewAdapter);
        homeViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Country>>() {
            @Override
            public void onChanged(ArrayList<Country> countries) {
                lottieAnimationView.setVisibility(View.GONE);
                txtTotalTerritories.setText(NumberFormatter.INSTANCE.formatToUnicode(countries.size()));
                mCountriesRecyclerViewAdapter.addCountries(countries);
                if (countries.size() == 0) {
                    txtError.setVisibility(View.VISIBLE);
                } else {
                    txtError.setVisibility(View.GONE);
                }
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
                Picasso.get().load(country.getCountryInfo().getFlag()).into(imgPreferredCountryFlag);
            }
        });
        homeViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                lottieAnimationView.setVisibility(View.GONE);
            }
        });
        txtTotalCases.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalCases()));
        txtTotalDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalDeaths()));
        txtTotalRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalRecovered()));
        txtTotalTerritories.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getTotalTerritories()));
        txtPreferredCountryCases.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryCases()));
        txtPreferredCountryDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryDeaths()));
        txtPreferredCountryRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(Session.getSession().getPreferredCountryRecovered()));
        txtPreferredCountry.setText(Session.getSession().getPreferredCountry());
        Picasso.get().load(Session.getSession().getPreferredCountryFlagUrl()).into(imgPreferredCountryFlag);
        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Country country = mCountriesRecyclerViewAdapter.getCountryAtPosition(mCountriesRecyclerViewAdapter.getPosition());
        switch (item.getItemId()) {
            case R.id.ctx_menu_add_preferred:
                Session.getSession().savePreferredCountry(country.getCountry());
                homeViewModel.getPreferredCountry();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        delayedRun();
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
