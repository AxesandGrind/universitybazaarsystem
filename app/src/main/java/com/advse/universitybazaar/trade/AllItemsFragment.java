package com.advse.universitybazaar.trade;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.advse.universitybazaar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllItemsFragment extends Fragment {

    TableLayout table ;
    TableRow tr;
    TextView tv1;
    TextView tv2;

    String ownerID;
    private DatabaseReference db;
    boolean finished = false;

    public AllItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // InflDte the layout for this fragment

        SharedPreferences prefs = this.getActivity().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
        ownerID = prefs.getString("mavID",null);
        System.out.println(ownerID);

        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        table = (TableLayout) view.findViewById(R.id.table);

        db = FirebaseDatabase.getInstance().getReference("Items/");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
