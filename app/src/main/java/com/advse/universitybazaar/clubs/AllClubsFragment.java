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
public class AllClubsFragment extends Fragment {


    public AllClubsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_clubs, container, false);
    }

}
