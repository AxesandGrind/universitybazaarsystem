package com.advse.universitybazaar.bean;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;

import com.advse.universitybazaar.register.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahsk0901 on 2/28/18.
 */

public class UserAdapter extends ArrayAdapter<Student>{

    private Context context;
    private List<Student> membersList;
    private DatabaseReference db;
    public UserAdapter(@NonNull Context context, int resource, List<Student> membersList) {
        super(context,resource,membersList);
        this.context = context;
        this.membersList = membersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        System.out.println("Adapter called");

        final Student student = membersList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.club_member_list_layout,parent,false);

        TextView clubMemberName = (TextView) convertView.findViewById(R.id.clubMemberName);
        Button deleteMemberButton = (Button) convertView.findViewById(R.id.deleteMember);

        clubMemberName.setText(student.getName());

        deleteMemberButton.setText("Delete");
        deleteMemberButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("Clubs/" + student.getClubId()+"/member/" + student.getMavID()).removeValue();
               /* db = FirebaseDatabase.getInstance().getReference();
                Query deleteMember = db.child("Clubs")*/

            }
        });
        return convertView;
    }

}
