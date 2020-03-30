package tech.minthura.carecovid.ui.settings;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.caresdk.Session;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView txtLanguage = root.findViewById(R.id.txt_language);
        txtLanguage.setInputType(InputType.TYPE_NULL);
        switch (Session.getSession().getCurrentLanguage()){
            case "my":
                txtLanguage.setText(R.string.app_unicode);
                break;
            case "zg":
                txtLanguage.setText(R.string.app_zawgyi);
                break;
            default:
                txtLanguage.setText(R.string.app_english);
                break;
        }
        root.findViewById(R.id.language_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showChooseLanguageDialog(getActivity(), true);
            }
        });
        root.findViewById(R.id.about_card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showAboutDialog(getContext());
            }
        });
//        txtLanguage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    DialogUtils.showChooseLanguageDialog(getActivity(), true);
//                    txtLanguage.clearFocus();
//                }
//            }
//        });
        return root;
    }
}
