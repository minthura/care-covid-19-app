package tech.minthura.carecovid.support;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.carecovid.BuildConfig;
import tech.minthura.carecovid.MainActivity;
import tech.minthura.carecovid.R;
import tech.minthura.caresdk.Session;

public class DialogUtils {

    private static AlertDialog mAlertDialog;

    public static AlertDialog showLoadingDialog(Context context, String message) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_generic_message, null);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation("virus_loading.json");
        lottieAnimationView.playAnimation();
        ((TextView)view.findViewById(R.id.txt_message)).setText(message);
        builder.setView(view);
        mAlertDialog = builder.show();
        return mAlertDialog;
    }

    public static void showUpdateDialog(Context context, String message, boolean force, String downloadUrl) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(context, !force);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_generic_message, null);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation("app_update_animation.json");
        lottieAnimationView.playAnimation();
        ((TextView)view.findViewById(R.id.txt_message)).setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        ((TextView)view.findViewById(R.id.txt_message)).setText(message);
        builder.setTitle(context.getString(R.string.app_update_available));
        builder.setView(view);
        builder.setPositiveButton(R.string.app_download, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                context.startActivity(browserIntent);
            }
        });
        mAlertDialog = builder.show();
    }

    public static void showMaintenanceDialog(Context context) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_generic_message, null);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation("error_maintenance.json");
        lottieAnimationView.playAnimation();
        ((TextView)view.findViewById(R.id.txt_message)).setText(R.string.app_maintenance_message);
        builder.setTitle(R.string.app_maintenance_title);
        builder.setView(view);
        builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mAlertDialog = builder.show();
    }


    public static void showGenericInfoDialog(Context context, String title, String message) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_generic_message, null);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation("info_animation.json");
        lottieAnimationView.playAnimation();
        ((TextView)view.findViewById(R.id.txt_message)).setText(message);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public static void showAboutDialog(Context context) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_about_app, null);
        ((TextView)view.findViewById(R.id.txt_app_version)).setText(BuildConfig.VERSION_NAME);
        builder.setView(view);
        builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public static void showChooseLanguageDialog(Activity activity, boolean cancelable) {
        CovidAppDialogBuilder builder = new CovidAppDialogBuilder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_change_language, null);
        RadioButton radioMMUnicode = view.findViewById(R.id.radio_mm_unicode);
        RadioButton radioMMZawgyi = view.findViewById(R.id.radio_mm_zawgyi);
        RadioButton radioEng = view.findViewById(R.id.radio_eng);
        builder.setTitle(R.string.app_choose_language);
        builder.setView(view);
        builder.setPositiveButton(R.string.app_use, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (radioMMUnicode.isChecked()) {
                    Session.getSession().updateLanguage("my");
                } else if (radioMMZawgyi.isChecked()) {
                    Session.getSession().updateLanguage("zg");
                } else {
                    Session.getSession().updateLanguage("en");
                }
                Intent intent = new Intent(activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        });
        if (cancelable){
            switch (Session.getSession().getCurrentLanguage()){
                case "my":
                    radioMMUnicode.setChecked(true);
                    break;
                case "zg":
                    radioMMZawgyi.setChecked(true);
                    break;
                default:
                    radioEng.setChecked(true);
                    break;
            }
            builder.setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            if(MDetect.INSTANCE.isUnicode()){
                radioMMUnicode.setChecked(true);
            } else {
                radioMMZawgyi.setChecked(true);
            }
        }
        builder.show();
    }

    public static void dismiss() {
        if (mAlertDialog != null && mAlertDialog.isShowing()){
            mAlertDialog.dismiss();
        }
    }

}
