package com.advse.universitybazaar.clubs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.advse.universitybazaar.bean.Club;
import com.advse.universitybazaar.bean.Student;
import com.advse.universitybazaar.bean.UserAdapter;
import com.advse.universitybazaar.register.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedClubHome extends AppCompatActivity {

    private DatabaseReference db;
    private Button requestMembership;
    private Button deleteClub;
    private TextView clubName;
    private TextView clubDescription;
    private TextView clubOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_club_home);


        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE);

        db = FirebaseDatabase.getInstance().getReference();

        final String mavID = "1234567890"; //sharedPreferences.getString("mavID", null);

        requestMembership = (Button) findViewById(R.id.requestMembership);
        deleteClub = (Button) findViewById(R.id.deleteClub);
        clubName = (TextView) findViewById(R.id.displayClubName);
        clubDescription = (TextView) findViewById(R.id.displayClubDescription);
        clubOwner = (TextView) findViewById(R.id.displayClubOwner);


        String mavIDMember = "1234567891";

        String mavIDUser = "1234567892";

        db.child("Clubs/" + 1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Club club = dataSnapshot.getValue(Club.class);
                if (club.getClubOwner().equals(mavID)) {

                    requestMembership.setVisibility(View.GONE);
                    deleteClub.setVisibility(View.VISIBLE);
                    clubName.setText(club.getClubName());
                    clubDescription.setText(club.getClubDescription());
                    Query getName = db.child("Users/" + club.getClubOwner());

                    getName.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snap) {
                            Student student = snap.getValue(Student.class);
                            clubOwner.setText(student.getName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().setTitle("Selected Club");
        List<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(new Student("1001358145","Sunny",1));
        ArrayAdapter<Student> listAdapter = new UserAdapter(getApplicationContext(),0,listOfStudents);

        ListView clubMembersList = (ListView) findViewById(R.id.listOfStudents);
        clubMembersList.setAdapter(listAdapter);
    }
}
