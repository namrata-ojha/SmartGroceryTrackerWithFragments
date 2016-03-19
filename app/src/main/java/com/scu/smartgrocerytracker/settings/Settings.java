package com.scu.smartgrocerytracker.settings;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scu.smartgrocerytracker.MapView;
import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.historygrocery.historyy;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.items.Items;


import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by namrataojha on 2/18/16.
 */
public class Settings extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.setting_layout, container, false);
        //Uninstall the app
        Button uninstall = (Button) rootView.findViewById(R.id.buttonUninstall);

        uninstall.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Uri packageURI = Uri.parse("package:com.javarticles.tabs");

                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

            }
        });
// Send feedback
        Button emailFeedback = (Button) rootView.findViewById(R.id.buttonSendFeedback);

        emailFeedback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "nojha@scu.edu,aganesh@scu.edu,nfernandes@scu.edu", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
//send bug
        Button reportBug = (Button) rootView.findViewById(R.id.buttonReportBug);

        reportBug.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "nojha@scu.edu,nfernandes@scu.edu,aganesh@scu.edu", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report Bug");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });


        Button buttonMaps = (Button) rootView.findViewById(R.id.buttonMaps);

        buttonMaps.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity() ,MapView.class);
                // myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);

            }
        });

//        Button buttonHistory = (Button) rootView.findViewById(R.id.buttonHistory);
//
//        buttonHistory.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//                Intent myIntent = new Intent(getActivity(),historyy.class);
//                // myIntent.putExtra("key", value); //Optional parameters
//                startActivity(myIntent);
//
//            }
//        });

        return rootView;
    }

    }
