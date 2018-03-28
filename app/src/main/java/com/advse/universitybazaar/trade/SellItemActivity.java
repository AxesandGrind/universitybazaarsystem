package com.advse.universitybazaar.trade;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.advse.universitybazaar.R;
import com.advse.universitybazaar.bean.Club;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellItemActivity extends AppCompatActivity {

    private DatabaseReference db;
    private Button sellItemButton;
    private EditText itemName;
    private EditText itemDescription;
    private EditText itemPrice;
    private TextInputLayout itemNameTIL;
    private TextInputLayout itemDescriptionTIL;
    private TextInputLayout itemPriceTil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);

        sellItemButton = (Button) findViewById(R.id.sellItemButton);
        itemName = (EditText) findViewById(R.id.itemName);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        itemPrice = (EditText) findViewById(R.id.itemPrice);
        itemNameTIL = (TextInputLayout) findViewById(R.id.itemNameTil);
        itemDescriptionTIL = (TextInputLayout) findViewById(R.id.itemDescTil);
        itemPriceTil = (TextInputLayout) findViewById(R.id.itemPriceTil);

        sellItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    createClub();
                }
            }
        });
    }

    public void createClub() {
        db = FirebaseDatabase.getInstance().getReference("items/");
        Query getLastRow = db.orderByKey().limitToLast(1);

        getLastRow.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  GenericTypeIndicator<ArrayList<Club>> clubsIndicator = new GenericTypeIndicator<ArrayList<Club>>() {};
                ArrayList<Club> clubList = new ArrayList<>();
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    clubList.add(snapShot.getValue(Club.class));
                }
                System.out.println(dataSnapshot);
                SharedPreferences prefs = getSharedPreferences("LOGIN_PREF",MODE_PRIVATE);
                String ownerID = prefs.getString("mavID",null);

                Club addClub = new Club(clubList.get(0).getClubId()+1,itemName.getText().toString(),
                        itemDescription.getText().toString(),ownerID);
                db.child(String.valueOf(clubList.get(0).getClubId()+1)).setValue(addClub);

                setResult(RESULT_OK,null);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean validate() {
        System.out.println("Validating");
        String name = itemName.getText().toString();
        String desc = itemDescription.getText().toString();
        String price = itemPrice.getText().toString();
        if(name.length() <= 0 && desc.length() <= 0 && price.length()<= 0) {
            Snackbar snack = Snackbar.make(findViewById(R.id.Snackbar_Sell_Item),"Please Enter all the fields",Snackbar.LENGTH_SHORT);
            snack.getView().setBackgroundColor(Color.parseColor("#B21919"));
            snack.show();
            return false;
        }
        else if(desc.length() > 150) {
            itemDescriptionTIL.setError("Description cannot be more than 150 characters");
            return false;
        }
        return true;
    }
}
