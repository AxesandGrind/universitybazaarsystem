package com.advse.universitybazaar.messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import com.advse.universitybazaar.bean.Club;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SendMessageActivity extends BaseActivity {

    public int getContentView() {
        return R.layout.activity_message_home;
    }

    public int getNavigationId() {
        return R.id.navigation_messages;
    }

    Spinner listOfReceivers = (Spinner) findViewById(R.id.spinner);
    Intent intent;
    DatabaseReference db;
    Button sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
    EditText messageBody = (EditText) findViewById(R.id.messageBody);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        intent = getIntent();


        getContentView();
        getNavigationId();

        Intent intent = getIntent();
        String msgType = intent.getStringExtra("messages");
        if(intent.getStringExtra("messages").equals("clubMessage")) {
            sendClubMessage();
        }

        if(msgType.equals("all")) {

            Button sendMessageButton = (Button) findViewById(R.id.sendMessageButton);

            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitBroadcastMessage();
                }
            });
        }
    }

    public void sendClubMessage() {
        final List<String> listOfClubs = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Clubs/");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Club club = snapShot.getValue(Club.class);
                    listOfClubs.add(String.valueOf(club.getClubId()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listOfClubs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOfReceivers.setAdapter(adapter);
            //Disable the spinner here
            //Message message = new Message();

        listOfReceivers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String clubId = listOfReceivers.getSelectedItem().toString();
                sendMessageToClubMembers(clubId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    public void sendMessageToClubMembers(String clubId) {
        final List<String> listOfReceivers = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Clubs").child(clubId).child("members");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    listOfReceivers.add(snapShot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
        String ownerID = prefs.getString("mavID",null);
        String messageContent = messageBody.getText().toString();

        for(String memberId : listOfReceivers) {

        }

    }

    public void submitBroadcastMessage() {
        final EditText messageBody = (EditText) findViewById(R.id.messageBody);
        SharedPreferences prefs = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
        final String senderId =  prefs.getString("mavID",null);

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("/All Messages");
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
        }
    }

