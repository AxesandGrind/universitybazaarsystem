package com.advse.universitybazaar.posts;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.register.UserHome;

public class PostHome extends AppCompatActivity {

   // final FloatingActionButton newPostButton = (FloatingActionButton) findViewById(R.id.addNewPostButton);
   // Button yourPostButton = (Button) findViewById(R.id.yourPostButton);
    Button othersPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_home);

        othersPostButton = (Button) findViewById(R.id.othersPostButton);

        /*newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });

        yourPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPostActivity.class);
                startActivity(intent);
            }
        });*/



        othersPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OthersPostActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),UserHome.class);
        startActivity(intent);
    }
}
