package com.advse.universitybazaar.clubs;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.advse.universitybazaar.register.R;
import com.advse.universitybazaar.register.UserHome;


public class ClubHome extends UserHome {

    private static final int requesId = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        FloatingActionButton createClubButon = (FloatingActionButton) findViewById(R.id.CreateClubButton);
        createClubButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createClubIntent = new Intent(getApplicationContext(), CreateClubActivity.class);
                startActivityForResult(createClubIntent, requesId);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode == requesId && resultCode == RESULT_OK) {
            Snackbar snack = Snackbar.make(findViewById(R.id.Snackbar_ClubHome),"Club created successfully",Snackbar.LENGTH_SHORT);
            snack.getView().setBackgroundColor(Color.parseColor("#298E10"));
            snack.show();
        } else if(resultCode == RESULT_CANCELED) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
