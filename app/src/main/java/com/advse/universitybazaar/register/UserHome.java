package com.advse.universitybazaar.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.BaseActivity;
import com.advse.universitybazaar.clubs.ClubHome;
import com.advse.universitybazaar.messages.MessageHome;
import com.advse.universitybazaar.posts.PostHome;
import com.advse.universitybazaar.trade.TradeActivity;

public class UserHome extends BaseActivity {


    public int getContentView() {
        return R.layout.activity_user_home;
    }

    public int getNavigationId() {
        return R.id.navigation_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getContentView();
        getNavigationId();

        SharedPreferences prefs = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
        String setTitle = prefs.getString("name",null);

        final Button logoutButton = (Button) findViewById(R.id.logout);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
                pref.edit().clear().apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}