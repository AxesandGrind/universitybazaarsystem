package com.advse.universitybazaar.clubs;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.advse.universitybazaar.register.R;



public class ClubHome extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout clubTabLayout;
    private ViewPager clubViewPager;

    private static final int requesId = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        /*getSupportActionBar().hide();*/

        FloatingActionButton createClubButon = (FloatingActionButton) findViewById(R.id.CreateClubButton);
        createClubButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createClubIntent = new Intent(getApplicationContext(), CreateClubActivity.class);
                startActivityForResult(createClubIntent, requesId);
            }
        });

        clubTabLayout = (TabLayout) findViewById(R.id.clubsTabLayout);
        clubViewPager = (ViewPager) findViewById(R.id.clubsPager);

        clubTabLayout.addTab(clubTabLayout.newTab().setText("All Clubs"));
        clubTabLayout.addTab(clubTabLayout.newTab().setText("Member Clubs"));
        clubTabLayout.addTab(clubTabLayout.newTab().setText("Owned Clubs"));
        clubTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ClubTabsManager manager = new ClubTabsManager(getSupportFragmentManager(), clubTabLayout.getTabCount());

        clubViewPager.setAdapter(manager);
        clubTabLayout.setOnTabSelectedListener(this);

        clubTabLayout.setupWithViewPager(clubViewPager);

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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        clubViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
