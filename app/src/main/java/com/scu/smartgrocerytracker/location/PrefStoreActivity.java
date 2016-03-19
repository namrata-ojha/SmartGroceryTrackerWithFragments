package com.scu.smartgrocerytracker.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;

import java.util.List;

public class PrefStoreActivity extends AppCompatActivity {
    SmartGroceryDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button search =(Button) findViewById(R.id.searchPrefStoreBtn);
        Button view = (Button) findViewById(R.id.viewPrefStoreBtn);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
                List<Place> places = dbHelper.getAllPreferredStores();
                ListView listView = (ListView) findViewById(R.id.prefStoreListView);
                listView.setAdapter(new ArrayAdapter<Place>(PrefStoreActivity.this, android.R.layout.simple_list_item_1, places));

            }
        });
        EditText miles = (EditText) findViewById(R.id.editText);
        final String prefMiles = miles.getText().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrefStoreActivity.this,PreferedLocationActivity.class);
                i.putExtra("Miles",prefMiles);
                startActivity(i);
            }
        });
    }

}
