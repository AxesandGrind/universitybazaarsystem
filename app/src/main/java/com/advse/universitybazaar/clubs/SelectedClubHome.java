package com.advse.universitybazaar.clubs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.advse.universitybazaar.bean.Club;
import com.advse.universitybazaar.bean.Student;
import com.advse.universitybazaar.bean.UserAdapter;
import com.advse.universitybazaar.register.R;
import com.advse.universitybazaar.register.RegisterActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectedClubHome extends AppCompatActivity {

    private DatabaseReference db;
    private DatabaseReference clubRef;
    private Button requestMembership;
    private Button deleteClub;
    private TextView clubName;
    private TextView clubDescription;
    private TextView clubOwner;
    private List<Student> listOfStudents;
    private UserAdapter listAdapter;
    private String mavID;
    private String userName;
    private String clubID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_club_home);

        // Get mavID from shared prefs and club id from intent bundle
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE);
        mavID = sharedPreferences.getString("mavID", null);
        userName = sharedPreferences.getString("name", null);
        Intent intent = getIntent();
        clubID = intent.getStringExtra("clubId");

        db = FirebaseDatabase.getInstance().getReference();
        clubRef = db.child("Clubs/" + clubID);

        requestMembership = (Button) findViewById(R.id.requestMembership);
        deleteClub = (Button) findViewById(R.id.deleteClub);
        clubName = (TextView) findViewById(R.id.displayClubName);
        clubDescription = (TextView) findViewById(R.id.displayClubDescription);
        clubOwner = (TextView) findViewById(R.id.displayClubOwner);

        listOfStudents = new ArrayList<>();
        listAdapter = new UserAdapter(getApplicationContext(),0,listOfStudents);

        ListView clubMembersList = (ListView) findViewById(R.id.listOfStudents);
        clubMembersList.setAdapter(listAdapter);


        clubRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Club club = dataSnapshot.getValue(Club.class);
                System.out.print("\n\n\n\n\n\n\n\n\n" + club);
                HashMap<String, String> members = new HashMap<String, String>();
                //clubList.add(snapShot.getValue(Club.class));
                for(DataSnapshot m : dataSnapshot.child("members").getChildren()){
                    listAdapter.add(new Student(m.getKey(),m.getValue().toString(),club.getClubId()));
                    members.put(m.getKey(), m.getValue().toString());
                }

                //To display name, owner and description
                clubName.setText(club.getClubName());
                getSupportActionBar().setTitle(club.getClubName());
                clubDescription.setText(club.getClubDescription());

                // Getting owner name using the owner's id
                Query getName = db.child("Users/" + club.getClubOwner());
                getName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snap) {
                        Student student = snap.getValue(Student.class);
                        clubOwner.setText(student.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                if (club.getClubOwner().equals(mavID)) {

                    requestMembership.setVisibility(View.GONE);
                    deleteClub.setVisibility(View.VISIBLE);
                    deleteClub.setText("Delete Club");
                    deleteClub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.child("Clubs/").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.child(String.valueOf(club.getClubId())).getRef().removeValue();
                                    Intent intent = new Intent(getApplicationContext(),ClubHome.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                }

                else if(!club.getClubOwner().equals(mavID) && members.containsKey(mavID)){
                    requestMembership.setVisibility(View.GONE);
                    deleteClub.setVisibility(View.GONE);
                }
                else{
                    Query getRequests = clubRef.child("requests");
                    getRequests.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snap) {
                            HashMap<String, String> requests = new HashMap<String, String>();
                            for(DataSnapshot r : snap.getChildren())
                                requests.put(r.getKey(), r.getValue().toString());

                            if(requests.containsKey(mavID)){
                                requestMembership.setVisibility(View.VISIBLE);
                                requestMembership.setText("Request Sent");
                            }
                            else{
                                requestMembership.setVisibility(View.VISIBLE);
                                deleteClub.setVisibility(View.GONE);
                                requestMembership.setText("Join");
                                requestMembership.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View v) {
                                        clubRef.child("requests").child(mavID).setValue(userName);
                                        requestMembership.setText("Request Sent");
                                        requestMembership.setOnClickListener(null);
                                        Toast.makeText(getApplicationContext(),"Request sent to the Owner",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });





        // Notifies as soon as a member is deleted from the club
        ChildEventListener memberdeletedListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // Add the code here.....
                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                //listAdapter.refreshMembersList(pass new list here);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        clubRef.addChildEventListener(memberdeletedListener);
    }
}
