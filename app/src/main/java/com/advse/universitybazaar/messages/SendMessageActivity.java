package com.advse.universitybazaar.messages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.BaseActivity;
import com.advse.universitybazaar.bean.Club;
import com.advse.universitybazaar.bean.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        Intent intent = getIntent();
        String msgType = intent.getStringExtra("messages");


        if(msgType.equals("all")) {

            Button sendMessageButton = (Button) findViewById(R.id.sendMessageButton);

            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //submitBroadcastMessage();
                }
            });

            //Disable the spinner here
            EditText messageBody = (EditText) findViewById(R.id.messageBody);
            SharedPreferences prefs = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
            String senderId =  prefs.getString("mavID",null);

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("/All Messages");
            Query getLastRow = db.orderByKey().limitToLast(1);

            getLastRow.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //  GenericTypeIndicator<ArrayList<Club>> clubsIndicator = new GenericTypeIndicator<ArrayList<Club>>() {};
                    ArrayList<Message> messageList = new ArrayList<>();
                    for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                        messageList.add(snapShot.getValue(Message.class));
                    }

                    int messageId = messageList.size() != 0 ? messageList.get(0).getMessageId() + 1 : 1;

                    Message message = new Message(messageId, messageBody.getText().toString(), senderId);

                    db.child(String.valueOf(messageId)).setValue(message);

                    setResult(RESULT_OK,null);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            /*submitBroadcastMessage() {

            }*/
            //Message message = new Message();
        }
    }
}
