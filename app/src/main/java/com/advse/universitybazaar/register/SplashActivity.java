package com.advse.universitybazaar.register;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
        },300);
    }
}
