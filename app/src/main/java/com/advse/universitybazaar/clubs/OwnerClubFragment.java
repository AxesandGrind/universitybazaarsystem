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
public class OwnerClubFragment extends Fragment {


    public OwnerClubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_club, container, false);
    }

}
