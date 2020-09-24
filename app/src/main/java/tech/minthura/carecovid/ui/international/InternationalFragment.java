package tech.minthura.carecovid.ui.international;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.adapter.CountriesRecyclerViewAdapter;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.carecovid.support.NumberFormatter;
import tech.minthura.carecovid.ui.base.BaseFragment;
import tech.minthura.carecovid.ui.home.HomeViewModel;
import tech.minthura.carecovid.view.EditTextBackEvent;
import tech.minthura.caresdk.model.Country;
import tech.minthura.caresdk.service.ErrorResponse;

public class InternationalFragment extends BaseFragment {
    private EditTextBackEvent searchView;
    private HomeListener mHomeListener;
    private CountriesRecyclerViewAdapter mCountriesRecyclerViewAdapter;
    private InternationalViewModel internationalViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            mHomeListener = (HomeListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        internationalViewModel = new ViewModelProvider(this).get(InternationalViewModel.class);
        View view = inflater.inflate(R.layout.fragment_international, container, false);
        TextView txtError = view.findViewById(R.id.txt_error);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mHomeListener.dismissSoftKeyboard();
                }
                return false;
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCountriesRecyclerViewAdapter.filter(s.toString());
            }
        });
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.loading_lottie_view);
        RecyclerView recyclerViewCountries = view.findViewById(R.id.recyclerViewCountries);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCountries.setLayoutManager(linearLayoutManager);
        mCountriesRecyclerViewAdapter = new CountriesRecyclerViewAdapter(getContext(), new ArrayList<>());
        recyclerViewCountries.setAdapter(mCountriesRecyclerViewAdapter);
        internationalViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Country>>() {
            @Override
            public void onChanged(ArrayList<Country> countries) {
                lottieAnimationView.setVisibility(View.GONE);
                mCountriesRecyclerViewAdapter.addCountries(countries);
                if (countries.size() == 0) {
                    txtError.setVisibility(View.VISIBLE);
                } else {
                    txtError.setVisibility(View.GONE);
                }
            }
        });
        internationalViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                lottieAnimationView.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        internationalViewModel.getCountriesData();
    }
}