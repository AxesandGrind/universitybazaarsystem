package com.advse.universitybazaar.clubs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advse.universitybazaar.register.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberClubFragment extends Fragment {


    public MemberClubFragment() {
        System.out.println("Inside Constructor");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Inside method");

        return inflater.inflate(R.layout.fragment_member_club, container, false);
    }

}
