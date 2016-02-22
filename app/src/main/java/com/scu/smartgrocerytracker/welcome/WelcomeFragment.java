package com.scu.smartgrocerytracker.welcome;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scu.smartgrocerytracker.R;


public class WelcomeFragment extends Fragment {
    //For fragments
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.welcome_layout, container, false);
        return rootView;
    }
}
