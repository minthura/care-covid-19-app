package tech.minthura.carecovid.ui.postdetail;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.carecovid.ui.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailFragment extends BaseFragment {

    public static final String ARG_TYPE = "ARG_TYPE";
    public static final String ARG_DESCRIPTION = "ARG_DESCRIPTION";
    private HomeListener mHomeListener;
    private String mType;
    private String mDescription;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            mHomeListener = (HomeListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
            mDescription = getArguments().getString(ARG_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        TextView txtDescription = view.findViewById(R.id.txt_post_description);
        View scrollLayout = view.findViewById(R.id.post_scroll_layout);
        WebView webView = view.findViewById(R.id.webView);
        if (mType.equals("TEXT")) {
            webView.setVisibility(View.GONE);
            txtDescription.setVisibility(View.VISIBLE);
            scrollLayout.setVisibility(View.VISIBLE);
            txtDescription.setText(MDetect.INSTANCE.getText(mDescription));
        } else {
            webView.setVisibility(View.VISIBLE);
            txtDescription.setVisibility(View.GONE);
            scrollLayout.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            String opening = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/mm3.ttf\")}body {font-family: Myanmar3;}</style></head><body>";
            String closing = "</body></html>";
            String myHtmlString = opening + mDescription + closing;
            webView.loadDataWithBaseURL("", myHtmlString, "text/html", "UTF-8", "");
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
