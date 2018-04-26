package com.advse.universitybazaar.messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.BaseActivity;

public class SendMessageActivity extends BaseActivity {

    public int getContentView() {
        return R.layout.activity_message_home;
    }

    public int getNavigationId() {
        return R.id.navigation_messages;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        

        getContentView();
        getNavigationId();



    }
}
