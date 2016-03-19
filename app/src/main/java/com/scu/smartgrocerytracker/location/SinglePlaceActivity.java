package com.scu.smartgrocerytracker.location;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SinglePlaceActivity extends Activity {

    SmartGroceryDBHelper dbHelper;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    InternetConnectionDetector cd;

       // Google Places
    GooglePlaces googlePlaces;

    // Place Details
    PlaceDetails placeDetails;

    // Progress dialog
    ProgressDialog pDialog;

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_place);

        Intent i = getIntent();

        // Place referece id
        String reference = i.getStringExtra(KEY_REFERENCE);

        // Calling a Async Background thread
        new LoadSinglePlaceDetails().execute(reference);

        dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
        Button save = (Button) findViewById(R.id.savebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insertPrefStore(placeDetails.result);

            }
        });

        Button view = (Button) findViewById(R.id.viewPrefStores);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView lbl_name = (TextView) findViewById(R.id.dispStoreName);
                TextView lbl_address = (TextView) findViewById(R.id.dispStoreAddr);
                TextView lbl_phone = (TextView) findViewById(R.id.dispStorePhnum);
                TextView lbl_lat = (TextView) findViewById(R.id.dispStoreLat);
                TextView lbl_lng = (TextView) findViewById(R.id.dispStoreLng);
                List<Place> places = dbHelper.getAllPreferredStores();
                for(Place place:places) {
                    lbl_name.setText(place.name);
                    lbl_address.setText(place.formatted_address);
                    lbl_phone.setText(place.formatted_phone_number);
                    lbl_lat.setText(place.geometry.location.lat+" ");
                    lbl_lng.setText(place.geometry.location.lng+" ");
                }
            }
        });

    }


    /**
     * Background Async Task to Load Google places
     * */
    class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SinglePlaceActivity.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Profile JSON
         * */
        protected String doInBackground(String... args) {
            String reference = args[0];

            // creating Places class object
            googlePlaces = new GooglePlaces();

            // Check if used is connected to Internet
            try {
                placeDetails = googlePlaces.getPlaceDetails(reference);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed Places into SinglePlaceActivity
                     * */
                    if(placeDetails != null){
                        String status = placeDetails.status;

                        // check place deatils status
                        // Check for all possible status
                        if(status.equals("OK")){
                            if (placeDetails.result != null) {
                                String name = placeDetails.result.name;
                                String address = placeDetails.result.formatted_address;
                                String phone = placeDetails.result.formatted_phone_number;
                                String latitude = Double.toString(placeDetails.result.geometry.location.lat);
                                String longitude = Double.toString(placeDetails.result.geometry.location.lng);


                                Log.d("Place ", name + address + phone + latitude + longitude);

                                // Displaying all the details in the view
                                // single_place.xml
                                TextView lbl_name = (TextView) findViewById(R.id.name);
                                TextView lbl_address = (TextView) findViewById(R.id.address);
                                TextView lbl_phone = (TextView) findViewById(R.id.phone);
                                TextView lbl_location = (TextView) findViewById(R.id.location);

                                // Check for null data from google
                                // Sometimes place details might missing
                                name = name == null ? "Not present" : name; // if name is null display as "Not present"
                                address = address == null ? "Not present" : address;
                                phone = phone == null ? "Not present" : phone;
                                latitude = latitude == null ? "Not present" : latitude;
                                longitude = longitude == null ? "Not present" : longitude;

                                lbl_name.setText(name);
                                lbl_address.setText(address);
                                lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
                                lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
                            }
                        }
                        else if(status.equals("ZERO_RESULTS")){
                            Toast.makeText(SinglePlaceActivity.this, "Sorry no places found.", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity","no places found");
                        }
                        else if(status.equals("UNKNOWN_ERROR"))
                        {
                            Toast.makeText(SinglePlaceActivity.this, "UNKNOWN_ERROR", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity", "UNKNOWN_ERROR");
                        }
                        else if(status.equals("OVER_QUERY_LIMIT"))
                        {
                            Toast.makeText(SinglePlaceActivity.this, "Query Limit reached", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity", "Query Limit reached");
                        }
                        else if(status.equals("REQUEST_DENIED"))
                        {
                            Toast.makeText(SinglePlaceActivity.this, "REQUEST_DENIED", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity", "REQUEST_DENIED");
                        }
                        else if(status.equals("INVALID_REQUEST"))
                        {
                            Toast.makeText(SinglePlaceActivity.this, "INVALID_REQUEST", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity", "INVALID_REQUEST");
                        }
                        else
                        {
                            Toast.makeText(SinglePlaceActivity.this, "Places Error", Toast.LENGTH_SHORT).show();
                            Log.i("SmartGrocery-SinglePlaceActivity", "Places Error");
                        }
                    }else{
                        Toast.makeText(SinglePlaceActivity.this, "Places Error", Toast.LENGTH_SHORT).show();
                        Log.i("SmartGrocery-SinglePlaceActivity", "Places Error");
                    }


                }
            });

        }

    }

}


