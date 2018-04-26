package com.advse.universitybazaar.messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.BaseActivity;
import com.github.clans.fab.FloatingActionMenu;

public class MessageHome extends BaseActivity {

    ViewPager messageViewPager;
    TabLayout messageTabLayout;
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    public int getContentView() {
        return R.layout.activity_message_home;
    }

    public int getNavigationId() {
        return R.id.navigation_messages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getContentView();
        getNavigationId();

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);

        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);

        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MessageHome.this,SendMessageActivity.class));
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MessageHome.this,SendMessageActivity.class));
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MessageHome.this,SendMessageActivity.class));
            }
        });
        messageViewPager = (ViewPager) findViewById(R.id.messagePager);
        setViewPager(messageViewPager);

        messageTabLayout = (TabLayout) findViewById(R.id.messageTabLayout);
        messageTabLayout.setupWithViewPager(messageViewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void setViewPager(ViewPager viewPager) {
        MessageTabsManager adapter = new MessageTabsManager(getSupportFragmentManager());
        adapter.addFragment(new AllMessageFragment(),"Announcements");
        adapter.addFragment(new ClubMessageFragment(),"Club Messages");
        adapter.addFragment(new PersonalMessageFragment(),"Personal Messages");
        viewPager.setAdapter(adapter);
    }
}
