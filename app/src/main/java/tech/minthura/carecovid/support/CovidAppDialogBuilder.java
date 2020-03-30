package tech.minthura.carecovid.support;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import tech.minthura.carecovid.R;

public class CovidAppDialogBuilder extends AlertDialog.Builder {

    public CovidAppDialogBuilder(@NonNull Context context) {
        super(context);
    }

    @Override
    public AlertDialog show() {
        AlertDialog dialog = super.show();
        dialog.setCancelable(false);
        Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (positive != null) {
            positive.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            positive.setTextColor(getContext().getResources().getColor(R.color.colorSuccess));
        }
        if (negative != null){
            negative.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            negative.setTextColor(getContext().getResources().getColor(R.color.colorFailure));
        }
        return dialog;
    }

}
