package com.advse.universitybazaar.clubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.advse.universitybazaar.bean.Student;
import com.advse.universitybazaar.bean.UserAdapter;
import com.advse.universitybazaar.register.R;

import java.util.ArrayList;
import java.util.List;

public class SelectedClubHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_club_home);
        getSupportActionBar().setTitle("Selected Club");
        List<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(new Student("1001358145","Sunny",1));
        ArrayAdapter<Student> listAdapter = new UserAdapter(getApplicationContext(),0,listOfStudents);

        ListView clubMembersList = (ListView) findViewById(R.id.listOfStudents);
        clubMembersList.setAdapter(listAdapter);
    }
}
