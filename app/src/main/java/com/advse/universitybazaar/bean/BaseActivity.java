package com.advse.universitybazaar.bean;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.clubs.ClubHome;
import com.advse.universitybazaar.messages.MessageHome;
import com.advse.universitybazaar.posts.PostHome;
import com.advse.universitybazaar.trade.TradeActivity;
import com.advse.universitybazaar.register.UserHome;
// Example can be found at link below
// https://blog.fossasia.org/using-activities-with-bottom-navigation-view-in-phimpme-android/
public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationShiftHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigation();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
//        navigation.postDelayed(() -> {
            int itemId = item.getItemId();

            if(itemId == R.id.navigation_news) {
                startActivity(new Intent(this,PostHome.class));
            } else if(itemId == R.id.navigation_clubs) {
                startActivity(new Intent(this,ClubHome.class));
            } else if(itemId == R.id.navigation_market) {
                startActivity(new Intent(this,TradeActivity.class));
            } else if(itemId == R.id.navigation_messages) {
                startActivity(new Intent(this, MessageHome.class));
            } else if(itemId == R.id.navigation_settings) {
                startActivity(new Intent(this,UserHome.class));
            }
//            finish();
//        },300);
        return true;
    }

    private void updateNavigation() {
        int id = getNavigationId();
        setNavigationItem(id);
    }

    private void setNavigationItem(int itemId) {
        MenuItem item = navigation.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    public abstract int getContentView();
    public abstract int getNavigationId();
}
