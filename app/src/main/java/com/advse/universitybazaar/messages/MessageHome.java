package com.advse.universitybazaar.messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.advse.universitybazaar.bean.BaseActivity;
import com.advse.universitybazaar.register.UserHome;
import com.advse.universitybazaar.R;

public class MessageHome extends BaseActivity {

    public int getContentView() {
        return R.layout.activity_message_home;
    }

    public int getNavigationId() {
        return R.id.navigation_messages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getContentView();
        getNavigationId();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),UserHome.class);
        startActivity(intent);
    }
}
