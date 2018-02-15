package com.advse.universitybazaar.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.advse.universitybazaar.bean.Student;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
        String currentPref =  pref.getString("mavID",null);
        if(currentPref != null) {
            Intent intent = new Intent(getApplicationContext(),UserHome.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);

        final Button loginButton = (Button) findViewById(R.id.login);
        final EditText mavID = (EditText) findViewById(R.id.mavID);
        final EditText password = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mID = mavID.getText().toString();
                final String pass = password.getText().toString();

                System.out.println("In Onclick");
                db = FirebaseDatabase.getInstance().getReference("Users/" + mID);
                System.out.println("Connection created");

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Student student = dataSnapshot.getValue(Student.class);
                        System.out.println("Datasnapshot captured");
                        if(student.getPassword().equals(pass)) {
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor preferences = getApplicationContext().getSharedPreferences("LOGIN_PREF",MODE_PRIVATE).edit();
                            preferences.putString("mavID",student.getMavID());
                            preferences.putString("name",student.getName());
                            preferences.apply();
                            Intent UserHome = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(UserHome);
                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                            mavID.setText("");
                            password.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
