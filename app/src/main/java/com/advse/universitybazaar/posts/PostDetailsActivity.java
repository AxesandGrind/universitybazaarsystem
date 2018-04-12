package com.advse.universitybazaar.posts;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.Comment;
import com.advse.universitybazaar.bean.Post;
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

    TableLayout tableLayout;
    TableRow tableRow;
    TableLayout.LayoutParams layoutparamstr;
    TableRow.LayoutParams layoutparams;
    TextView commenter;
    TextView commentText;

    ImageButton imageButton;

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

        tableLayout = (TableLayout) findViewById(R.id.tableComments);

        imageButton = (ImageButton) findViewById(R.id.postCommentButton);

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


        //To fetch and update comments

        db = FirebaseDatabase.getInstance().getReference("Posts/"+ intent.getStringExtra("postId") + "comments");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);

                        addToView(comment);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // to add comment

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void addToView(Comment comment){

        tableRow = new TableRow(this);
        layoutparamstr = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT);
        layoutparamstr.setMargins(0, 30,0, 30);
        tableRow.setLayoutParams(layoutparamstr);
        tableRow.setBackgroundColor(Color.GRAY);
        tableRow.setPadding(0, 40, 0, 40);

        layoutparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 20);



        commenter = new TextView(this);
        commenter.setLayoutParams(layoutparams);
        commenter.setGravity(Gravity.CENTER);
        commenter.setText(comment.getOwnerId());
        commenter.setTextSize(15);
        tableRow.addView(commenter);

        layoutparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 80);

        commentText = new TextView(this);
        commentText.setLayoutParams(layoutparams);
        commentText.setGravity(Gravity.CENTER);
        commentText.setText(comment.getCommentText());
        commentText.setTextSize(15);
        tableRow.addView(commentText);

        tableLayout.addView(tableRow);

    }

}
