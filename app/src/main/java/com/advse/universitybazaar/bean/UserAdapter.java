package com.advse.universitybazaar.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;

import com.advse.universitybazaar.register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahsk0901 on 2/28/18.
 */

public class UserAdapter extends ArrayAdapter<Student>{

    private Context context;
    private List<Student> membersList;
    private DatabaseReference db;
    public UserAdapter(@NonNull Context context, int resource, List<Student> membersList) {
        super(context,resource,membersList);
        this.context = context;
        this.membersList = membersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = FirebaseDatabase.getInstance().getReference();

        SharedPreferences prefs = context.getSharedPreferences("LOGIN_PREF",Context.MODE_PRIVATE);
        final String currentUser = prefs.getString("mavID",null);

        final Student student = membersList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.club_member_list_layout,parent,false);

        final TextView clubMemberName = (TextView) convertView.findViewById(R.id.clubMemberName);
        final Button deleteMemberButton = (Button) convertView.findViewById(R.id.deleteMember);
        deleteMemberButton.setVisibility(View.GONE);

        clubMemberName.setText(student.getName());

        db.child("Clubs/" + student.getClubId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Club club = dataSnapshot.getValue(Club.class);
                if(club.getClubOwner().equals(currentUser) && student.getType().equals("M")) {
                    deleteMemberButton.setText("Delete");
                    deleteMemberButton.setVisibility(View.VISIBLE);
                    deleteMemberButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            db = db.child("Clubs").child(String.valueOf(student.getClubId())).child("members").child(student.getMavID());
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().removeValue();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }
                else {
                    deleteMemberButton.setText("Approve");
                    deleteMemberButton.setVisibility(View.VISIBLE);
                    deleteMemberButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            db = db.child("Clubs").child(String.valueOf(student.getClubId())).child("requests").child(student.getMavID());
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    db.getParent().getParent().child("members").child(student.getMavID()).setValue(student.getName());
                                    dataSnapshot.getRef().removeValue();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*db.child("Clubs/" + student.getClubId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        return convertView;
    }

    public void refreshMembersList(List<Student> membersList) {
        this.membersList.clear();
        this.membersList.addAll(membersList);
        notifyDataSetChanged();
    }

}
