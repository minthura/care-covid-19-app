package tech.minthura.carecovid;

import android.content.Intent;
import android.os.Bundle;

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
                R.id.navigation_home, R.id.navigation_tips, R.id.navigation_settings)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationMessageEvent(NotificationMessageEvent event) {
        DialogUtils.showGenericInfoDialog(this, MDetect.INSTANCE.getText(event.getTitle()), MDetect.INSTANCE.getText(event.getMessage()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Session.getSession().setState(Session.State.OPEN);
        Session.getSession().postLastNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        Session.getSession().setState(Session.State.CLOSED);
    }

}
