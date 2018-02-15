package com.advse.universitybazaar.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.advse.universitybazaar.clubs.ClubHome;
import com.advse.universitybazaar.messages.MessageHome;
import com.advse.universitybazaar.posts.PostHome;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        final Button clubHome = (Button) findViewById(R.id.clubButton);
        final Button postsHome = (Button) findViewById(R.id.postButton);
        final Button mesageHome = (Button) findViewById(R.id.messageButton);
        final Button logoutButton = (Button) findViewById(R.id.logout);

        clubHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClubHome.class);
                startActivity(intent);
            }
        });

        postsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostHome.class);
                startActivity(intent);
            }
        });

        mesageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageHome.class);
                startActivity(intent);
            }
        });

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