package com.advse.universitybazaar.posts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailsActivity extends AppCompatActivity {

    TextView headingTextView;
    TextView descriptionTextView;
    TextView locationTextView;
    Button updateButton;
    Button deleteButton;
    DatabaseReference db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        intent = getIntent();

        headingTextView = (TextView) findViewById(R.id.postHeading);
        headingTextView.setText(intent.getStringExtra("postHeading"));

        descriptionTextView = (TextView) findViewById(R.id.postDescription);
        descriptionTextView.setText(intent.getStringExtra("postDescription"));

        locationTextView = (TextView) findViewById(R.id.postLocation);
        locationTextView.setText(intent.getStringExtra("postLocation"));

        updateButton = (Button) findViewById(R.id.updatePost);
        deleteButton = (Button) findViewById(R.id.deletePost);

        if(intent.getStringExtra("yourPost").equals("0")) {

            updateButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);

        }else {

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance().getReference("Posts/");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child(intent.getStringExtra("postId")).child("postHeading").
                                    setValue(headingTextView.getText().toString());

                            dataSnapshot.getRef().child(intent.getStringExtra("postId")).child("postDescription").
                                    setValue(descriptionTextView.getText().toString());

                            dataSnapshot.getRef().child(intent.getStringExtra("postId")).child("postLocation").
                                    setValue(locationTextView.getText().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance().getReference("Posts/");
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.child(intent.getStringExtra("postId")).getRef().removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });
        }


    }

}
