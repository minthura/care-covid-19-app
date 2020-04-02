package tech.minthura.carecovid.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.adapter.CVInfoWindowAdapter;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.carecovid.support.NumberConverter;
import tech.minthura.carecovid.support.NumberFormatter;
import tech.minthura.caresdk.model.Hospital;
import tech.minthura.caresdk.service.ErrorResponse;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private MapViewModel mMapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        mMapViewModel.getHospitalsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hospital>>() {
            @Override
            public void onChanged(ArrayList<Hospital> hospitals) {
                DialogUtils.dismiss();
                boolean movePosition = true;
                for(Hospital hospital : hospitals) {
                    LatLng position = new LatLng(hospital.getLatitude(), hospital.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(hospital.getHospital())
                            .snippet(getSnippet(
                                    hospital.getTownship(),
                                    hospital.getPui(),
                                    hospital.getPending(),
                                    hospital.getSuspected(),
                                    hospital.getConfirmed(),
                                    hospital.getDeath(),
                                    hospital.getRecovered()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital)));
                    mMap.setInfoWindowAdapter(new CVInfoWindowAdapter(getContext()));
                    if (movePosition){
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 5.5f));
                        movePosition = false;
                    }
                }
            }
        });
        mMapViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                DialogUtils.dismiss();
            }
        });
        return view;
    }

    private String getSnippet(String township, int pui, int pending, int suspected, int confirmed, int death, int recovered){
        return String.format(Locale.getDefault(),"%1$s - %2$s\n%3$s - %4$s\n%5$s - %6$s\n%7$s - %8$s\n%9$s - %10$s\n%11$s - %12$s\n%13$s - %14$s"
        , getString(R.string.app_map_township), township,
                getString(R.string.app_map_pui), NumberFormatter.INSTANCE.formatToUnicode(pui),
                getString(R.string.app_map_suspected), NumberFormatter.INSTANCE.formatToUnicode(suspected),
                getString(R.string.app_map_confirmed), NumberFormatter.INSTANCE.formatToUnicode(confirmed),
                getString(R.string.app_map_deaths), NumberFormatter.INSTANCE.formatToUnicode(death),
                getString(R.string.app_map_recovered), NumberFormatter.INSTANCE.formatToUnicode(recovered),
                getString(R.string.app_map_pending), NumberFormatter.INSTANCE.formatToUnicode(pending));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.7633, 96.0785), 5.5f));
        DialogUtils.showLoadingDialog(getContext(), getString(R.string.app_loading));
        mMapViewModel.getHospitals();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
