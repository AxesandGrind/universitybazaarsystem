package com.advse.universitybazaar.trade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.clubs.ClubHome;

public class TradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        SharedPreferences prefs = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
        String setTitle = prefs.getString("name",null);

        Button boughtItemsButton = (Button) findViewById(R.id.bought);

        boughtItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoughtItemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
