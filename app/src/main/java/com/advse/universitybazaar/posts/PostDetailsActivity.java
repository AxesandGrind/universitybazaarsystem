package com.advse.universitybazaar.posts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailsActivity extends AppCompatActivity {

    EditText headingTextView;
    EditText descriptionTextView;
    EditText locationTextView;

    Button updateButton;
    Button deleteButton;
    Button savePostButton;

    DatabaseReference db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        getSupportActionBar().hide();
        intent = getIntent();

        headingTextView = (EditText) findViewById(R.id.postHeading);
        headingTextView.setText(intent.getStringExtra("postHeading"));

        descriptionTextView = (EditText) findViewById(R.id.postDescription);
        descriptionTextView.setText(intent.getStringExtra("postDescription"));

        locationTextView = (EditText) findViewById(R.id.postLocation);
        locationTextView.setText(intent.getStringExtra("postLocation"));

        updateButton = (Button) findViewById(R.id.updatePost);
        deleteButton = (Button) findViewById(R.id.deletePost);
        savePostButton = (Button) findViewById(R.id.savePost);

        if(intent.getStringExtra("yourPost").equals("0")) {

            updateButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
            savePostButton.setVisibility(View.INVISIBLE);

        }else {

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    headingTextView.setFocusable(true);
                    headingTextView.requestFocus();
                    descriptionTextView.setFocusable(true);
                    locationTextView.setFocusable(true);

                    updateButton.setVisibility(View.INVISIBLE);
                    savePostButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                }
            });

            savePostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance().getReference("Posts/").child(intent.getStringExtra("postId"));
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("postHeading").
                                    setValue(headingTextView.getText().toString());

                            dataSnapshot.getRef().child("postDescription").
                                    setValue(descriptionTextView.getText().toString());

                            dataSnapshot.getRef().child("postLocation").
                                    setValue(locationTextView.getText().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    headingTextView.setFocusable(false);
                    descriptionTextView.setFocusable(false);
                    locationTextView.setFocusable(false);

                    updateButton.setVisibility(View.VISIBLE);
                    savePostButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);


                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance().getReference("Posts/").child(intent.getStringExtra("postId"));
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    setResult(RESULT_OK,null);
                    finish();
                }
            });
        }


    }

}
