package tech.minthura.carecovid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.carecovid.ui.postdetail.PostDetailFragment;
import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.NotificationMessageEvent;
import tech.minthura.caresdk.model.UpdateCheck;
import tech.minthura.caresdk.service.CovidApiCallback;
import tech.minthura.caresdk.service.Error;
import tech.minthura.caresdk.service.ErrorResponse;

public class MainActivity extends BaseActivity implements HomeListener {

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map ,R.id.navigation_tips, R.id.navigation_settings)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, mNavController);
    }

    @Override
    public void onPostDetails(String type, String description) {
        Bundle bundle = new Bundle();
        bundle.putString(PostDetailFragment.ARG_TYPE, type);
        bundle.putString(PostDetailFragment.ARG_DESCRIPTION, description);
        mNavController.navigate(R.id.action_navigation_tips_to_details_fragment, bundle);
    }

    @Override
    public void onFullScreenImageView(String url) {
        Intent intent = new Intent(this, FullScreenImageViewActivity.class);
        intent.putExtra( FullScreenImageViewActivity.ARG_IMAGE_URL, url);
        startActivity(intent);
    }

    @Override
    public void dismissSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationMessageEvent(NotificationMessageEvent event) {
        if (event.getType() == NotificationMessageEvent.NotificationType.NEW_CASE){
            DialogUtils.showNewCasesInfoDialog(this, MDetect.INSTANCE.getText(event.getTitle()), MDetect.INSTANCE.getText(event.getMessage()));
        } else {
            DialogUtils.showGenericInfoDialog(this, MDetect.INSTANCE.getText(event.getTitle()), MDetect.INSTANCE.getText(event.getMessage()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Session.getSession().setState(Session.State.OPEN);
        Session.getSession().postLastNotification();
        DialogUtils.dismiss();
        Session.getSession().updateCheck(new CovidApiCallback<UpdateCheck>() {
            @Override
            public void onSuccess(UpdateCheck updateCheck) {
                DialogUtils.showUpdateDialog(MainActivity.this, MDetect.INSTANCE.getText(updateCheck.getChangeLog()), updateCheck.isForce(), updateCheck.getDownloadUrl());
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                if (errorResponse.getError() == Error.ERROR_SERVER_MAINTENANCE) {
                    DialogUtils.showMaintenanceDialog(MainActivity.this);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        Session.getSession().setState(Session.State.CLOSED);
    }

}
