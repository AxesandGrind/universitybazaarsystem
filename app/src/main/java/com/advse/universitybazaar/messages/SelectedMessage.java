package com.advse.universitybazaar.messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.advse.universitybazaar.R;

public class SelectedMessage extends AppCompatActivity {

    TextView messageSenderId;
    TextView messageDescription;
    TextView messageHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_message);
        Intent intent = getIntent();
        String[] messageDetails = intent.getStringExtra("messsage").split(",");
        setContentView(R.layout.activity_selected_message);
        messageSenderId = (TextView) findViewById(R.id.displayMessageSenderId);
        messageDescription = (TextView) findViewById(R.id.displayMessageDescription);
        messageHeading = (TextView) findViewById(R.id.displayMessageHeading);
        messageHeading.setText(messageDetails[0]);
        messageSenderId.setText("From: " + messageDetails[1]);
        messageDescription.setText("Message \n");
        if(messageDetails.length>2)
            messageDescription.setText(messageDescription.getText() + messageDetails[2]);
    }
}
