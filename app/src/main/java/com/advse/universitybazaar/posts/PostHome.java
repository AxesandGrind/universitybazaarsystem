package com.advse.universitybazaar.posts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.register.UserHome;

public class PostHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_home);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),UserHome.class);
        startActivity(intent);
    }
}
