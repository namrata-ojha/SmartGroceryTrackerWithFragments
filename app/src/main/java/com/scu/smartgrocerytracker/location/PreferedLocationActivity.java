package com.scu.smartgrocerytracker.location;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.smartgrocerytracker.R;


public class PreferedLocationActivity extends Activity {


    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    InternetConnectionDetector cd;

     // Google Places
    GooglePlaces googlePlaces;

    // Places List
    PlacesList nearPlaces;

    // GPS Location
    GPSTracker gps;

    // Button
    Button btnShowOnMap;

    // Progress dialog
    ProgressDialog pDialog;

    // Places Listview
    ListView lv;

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();


    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefered_location);

        cd = new InternetConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            Toast.makeText(PreferedLocationActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        }

        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
           Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
         //  Log.d("Your Location", "latitude:" + lat + ", longitude: " + lng);
        } else {
            // Can't get user's current location
            Toast.makeText(PreferedLocationActivity.this, "Couldn't get location information. Please enable GPS", Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        }

        // Getting listview
        lv = (ListView) findViewById(R.id.list);

       /* // button show on map
        btnShowOnMap = (Button) findViewById(R.id.btn_show_map);
*/
        // calling background Async task to load Google Places
        // After getting places from Google all the data is shown in listview
        new LoadPlaces().execute();
        /**
         * ListItem click event
         * On selecting a listitem SinglePlaceActivity is launched
         * */
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        SinglePlaceActivity.class);

                // Sending place refrence id to single place activity
                // place refrence id used to get "Place full details"
                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
    }

    /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PreferedLocationActivity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();

            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "grocery_or_supermarket"; // Listing places only cafes, restaurants

                // Radius in meters - increase this value if you don't find any places
                Bundle bundle = getIntent().getExtras();
                String miles = null;
                if(bundle.getString("Miles") !=null ) {
                   miles  = bundle.getString("Miles");
                }
                double milesInDouble = Double.parseDouble(miles);
                double radius = milesInDouble/0.00062137;
                Log.i("radius",radius+"");

                // get nearest places
               nearPlaces = googlePlaces.search(gps.getLatitude(),gps.getLongitude(), radius, types);

             //   nearPlaces = googlePlaces.search(lat,lng, radius, types);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            if(pDialog != null && pDialog.isShowing()){
                pDialog.dismiss();
            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into LISTVIEW
                     * */
                    // Get json response status
                    String status = nearPlaces.status;

                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place p : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();

                                // Place reference won't display in listview - it will be hidden
                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, p.reference);

                                // Place name
                                map.put(KEY_NAME, p.name);


                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }
                            // list adapter
                            ListAdapter adapter = new SimpleAdapter(PreferedLocationActivity.this, placesListItems,
                                    R.layout.location_list_row,
                                    new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                    R.id.reference, R.id.name });

                            // Adding data into listview
                            lv.setAdapter(adapter);
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        // Zero results found
                        Toast.makeText(PreferedLocationActivity.this,"Sorry no places found.",Toast.LENGTH_SHORT).show();
                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {

                        Toast.makeText(PreferedLocationActivity.this,"Sorry no places found.",Toast.LENGTH_SHORT).show();
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        Toast.makeText(PreferedLocationActivity.this,"Sorry no places found.",Toast.LENGTH_SHORT).show();
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        Toast.makeText(PreferedLocationActivity.this,"Error occurred.Request denied.",Toast.LENGTH_SHORT).show();
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                       Toast.makeText(PreferedLocationActivity.this,"Error occurred. Invalid Request.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(PreferedLocationActivity.this,"Sorry error occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}