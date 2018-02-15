package com.advse.universitybazaar.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.advse.universitybazaar.bean.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference db;
    private Button b1;
    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText t4;
    private String err = "";
    private boolean succsess;

    private static final String coDomain = "mavs.uta.edu";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + Pattern.quote(coDomain) + "$";
    private static final String PW_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[@#$%]).{8,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        succsess = false;

        b1 = (Button) findViewById(R.id.b1);
        //When reserve button is clicked
        b1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                doRegister();
            }
        });
    }
    public void doRegister(){
        String err = "";
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        t3 = (EditText) findViewById(R.id.t3);
        t4 = (EditText) findViewById(R.id.t4);

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(t3.getText().toString().trim());

        Pattern pattern2 = Pattern.compile(PW_PATTERN);
        Matcher matcher2 = pattern2.matcher(t4.getText().toString().trim());

        if(t1.getText().toString().trim().length() <= 0
                || t2.getText().toString().trim().length() <= 0
                || t3.getText().toString().trim().length() <= 0
                || t4.getText().toString().trim().length() <= 0){

            err = err + "Please fill in all the fields \n";

        }

        if(t2.getText().toString().trim().length() != 10)
            err = err + "Student Id must be valid 10 digit number \n";

        if(!matcher.matches())
            err= err + "Email address should be in the format someone@mavs.uta.edu\n";

        if(!matcher2.matches())
            err= err + "Password must have 8 characters with at least 1 digit, 1 alphabet and one special char from @#$%\n";


        if(!err.isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
            dialog.setCancelable(true);
            dialog.setTitle("Registration Error");
            dialog.setMessage(err);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Intent registerPage = new Intent(Registration.this, Registration.class);
                    //startActivity(registerPage);
                }
            });

            final AlertDialog alert = dialog.create();
            alert.show();
        }

        //If no validation issues
        else{
            db = FirebaseDatabase.getInstance().getReference("Users");
            System.out.println("Connection created");

            db.child(t2.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    System.out.println("Datasnapshot captured");
                    if(student == null) {
                        sendEmail();
                        Student newStudent = new Student(t2.getText().toString().trim(),
                                t1.getText().toString().trim(),
                                t4.getText().toString().trim(),
                                t3.getText().toString().trim()  );

                        db.child(t2.getText().toString().trim()).setValue(newStudent);
                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                        Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginPage);
                    } else {
                        Toast.makeText(getApplicationContext(),"User already exists in the system",Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





        }

    }

    public void sendEmail(){
        //Toast.makeText(getApplicationContext(),"Email function called",Toast.LENGTH_LONG).show();
        new EmailActivity(this,t3.getText().toString().trim()).execute(t3.getText().toString().trim());

    }
}