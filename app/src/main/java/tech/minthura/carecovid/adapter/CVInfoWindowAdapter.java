package tech.minthura.carecovid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import tech.minthura.carecovid.R;

public class CVInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final Context mContext;

    public CVInfoWindowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.map_marker_snippet, null, true);
        ((TextView)view.findViewById(R.id.title)).setText(marker.getTitle());
        ((TextView)view.findViewById(R.id.snippet)).setText(marker.getSnippet());
        return view;
    }
}
