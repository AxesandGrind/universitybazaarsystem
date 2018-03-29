package com.advse.universitybazaar.trade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.Item;

public class ItemDetails extends AppCompatActivity {

    TextView nameTextView;
    TextView descriptionView;
    TextView priceTextView;
    Button buyItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent = getIntent();

        nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(intent.getStringExtra("name"));

        descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(intent.getStringExtra("description"));

        priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText(intent.getStringExtra("price"));

        buyItemButton = (Button) findViewById(R.id.buyItem);

        if(intent.getStringExtra("buy").equals("0")) {

            buyItemButton.setEnabled(false);

        }

    }
}
