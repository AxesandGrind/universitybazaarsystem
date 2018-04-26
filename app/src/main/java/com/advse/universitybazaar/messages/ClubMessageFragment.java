package com.advse.universitybazaar.messages;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.Club;
import com.advse.universitybazaar.bean.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;

public class ClubMessageFragment extends Fragment {
    TableLayout tableLayout;
    TableRow tableRow;
    TextView textView;
    View view;
    String ownerID;
    DatabaseReference db;
    TableLayout.LayoutParams layoutparamstr;
    TableRow.LayoutParams layoutparams;


    public ClubMessageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addMessageToView(Message message) {
        tableRow = new TableRow(getActivity());
        layoutparamstr = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT);
        layoutparamstr.setMargins(0, 30,0, 30);
        tableRow.setLayoutParams(layoutparamstr);
        tableRow.setId(message.getMessageId());
        tableRow.setBackgroundColor(Color.GRAY);
        tableRow.setPadding(0, 40, 0, 40);
        layoutparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 50);
        tableRow.setOnClickListener(new TableRow.OnClickListener(){
            public void onClick(View v) {
                showMessage(message);
            }
        });

        textView = new TextView(getActivity());
        textView.setLayoutParams(layoutparams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(message.getSenderId());
        textView.setTextSize(15);
        tableRow.addView(textView);
        tableLayout.addView(tableRow);
    }

    private void showMessage(Message message) {
        Intent selectedMessage = new Intent(getActivity().getApplicationContext(), com.advse.universitybazaar.messages.SelectedMessage.class);
        String messageDetails = "Club Messages,"+ message.getSenderId()+","+message.getMessageDesc();
        selectedMessage.putExtra("messsage",messageDetails);
        startActivityForResult(selectedMessage,1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_messages,container,false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
        ownerID = prefs.getString("mavID",null);
        tableLayout = (TableLayout) view.findViewById(R.id.table);

        db = FirebaseDatabase.getInstance().getReference("Clubs/");


        db.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                while (tableLayout.getChildCount() > 1)
                    tableLayout.removeView(tableLayout.getChildAt(tableLayout.getChildCount() - 1));

                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    //System.out.println(snapShot);
                    HashMap<String, String> members = new HashMap<String, String>();
                    //clubList.add(snapShot.getValue(Club.class));
                    for(DataSnapshot m : snapShot.child("members").getChildren()){
                        members.put(m.getKey(), m.getValue().toString());
                        //System.out.println(m);
                    }
                    if(!members.isEmpty() && members.containsKey(ownerID )) {
                        for(DataSnapshot m : snapShot.child("messages").getChildren()){
                            Message message = m.getValue(Message.class);
                            addMessageToView(message);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Event cancelled");
            }

        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
