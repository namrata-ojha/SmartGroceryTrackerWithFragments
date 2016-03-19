package com.scu.smartgrocerytracker.location;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.items.ItemListingActivity;
import com.scu.smartgrocerytracker.items.Items;

import java.util.List;

public class PrefStoreActivity extends Fragment {
    SmartGroceryDBHelper dbHelper;
    ListView listView;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pref_store);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_pref_store, container, false);
        //Uninstall the app
//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Button search =(Button) rootView.findViewById(R.id.searchPrefStoreBtn);
        Button view = (Button) rootView.findViewById(R.id.viewPrefStoreBtn);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());
                List<Place> places = dbHelper.getAllPreferredStores();
                listView = (ListView) rootView.findViewById(R.id.prefStoreListView);
                listView.setAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_1, places));



                ////by namrata just trial

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Place itemClicked = (Place) parent.getItemAtPosition(position);
                      Double lat =  itemClicked.getGeometry().getLocation().getLat();
                        Double lag =  itemClicked.getGeometry().getLocation().getLng();
                        itemClicked.getFormatted_address();
                        String togo = Double.toString(lat)+","+Double.toString(lag);
                        String from= "37.349,-121.95";

//                        Toast toastNotConnected = Toast.makeText(getActivity().getApplicationContext(),  Double.toString(lat), Toast.LENGTH_LONG);
//                        toastNotConnected.show();
                        String map ="http://maps.google.com/maps?saddr="+from+"&daddr="+togo;
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse(map));
                        startActivity(intent);

                    }

                });

            }
        });
        EditText miles = (EditText) rootView.findViewById(R.id.editText);
        final String prefMiles = miles.getText().toString();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PreferedLocationActivity.class);
                i.putExtra("Miles", prefMiles);
                startActivity(i);
            }
        });



        return rootView;
    }

}
