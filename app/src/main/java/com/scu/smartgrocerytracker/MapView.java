package com.scu.smartgrocerytracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapView extends AppCompatActivity implements OnMapReadyCallback {

    private Button mGoButton;
    private EditText mPlaceEntry;
    private TextView mDisplayResults;
    String entry;

    final String GoogleKey = "AIzaSyC70Yif2OtaySWM7d4gR1SRPGSgGTRHJYc";
    final String GoogleKey2 = "AIzaSyDMX69her6nrZ_FrnK52ows1dVf5hF6Cpc";

//    ArrayList<String> names = new ArrayList<>();
//    ArrayList<Double> lati = new ArrayList<>();
//    ArrayList<Double> longi = new ArrayList<>();



    GoogleMap gMap = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);





        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        mGoButton = (Button) findViewById(R.id.go);
//        mPlaceEntry = (EditText) findViewById(R.id.place_entry);
//        mDisplayResults = (TextView) findViewById(R.id.display_Results);




//        mGoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                entry = String.valueOf(mPlaceEntry.getText());
//                HttpAsyncTask h = new HttpAsyncTask();
//                h.execute("https://maps.googleapis.com/maps/api/place/textsearch/json?query=grocery+stores+in+" + entry + "&key=AIzaSyDMX69her6nrZ_FrnK52ows1dVf5hF6Cpc");
//
//
//            }
//
//        });




    }


    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.d("result", result);
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        Log.d("result2", result);
        inputStream.close();
        return result;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        gMap.addMarker(new MarkerOptions().position(new LatLng(37.3460111, -121.8705815)).title("Chaparral")); //

        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.3460111, -121.8705815)));




        gMap.addMarker(new MarkerOptions().position(new LatLng(37.3279277, -121.9314152)).title("Zanotto's Family Market"));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.3279277, -121.9314152)));



        gMap.addMarker(new MarkerOptions().position(new LatLng(37.3149031, -121.9780012)).title("Mitsuwa Marketplace"));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.3149031, -121.9780012)));



        gMap.addMarker(new MarkerOptions().position(new LatLng(37.2926571, -121.9923749)).title("Sprouts Farmers Market"));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.2926571, -121.9923749)));



        gMap.addMarker(new MarkerOptions().position(new LatLng(37.3492, -121.9381)).title("Your Location "));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.3588, -121.9688)));



        // gMap.addMarker(new MarkerOptions().position(new LatLng(lati.get(0), longi.get(0))).title("Marker"));
       // gMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lati.get(0),longi.get(0))));

//        while(gMap!=null)
//        {
//            for (int i = 0; i < names.size(); i++) {
//                        googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(lati.get(i), longi.get(i)))
//                                .title(names.get(i)));
////                    result1 += names.get(i) + " " + lati.get(i) + longi.get(i) + "\n";
//            }
//            break;
//        }


    }




    public class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // Toast.makeText( getActivity().getApplicationContext(), "Received!", Toast.LENGTH_LONG).show();
            // Toast.makeText( getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObj = new JSONObject(result);
                StringBuilder builderAddress = new StringBuilder();
                StringBuilder builderLat = new StringBuilder();

                //now read
                JSONArray jsonObject1 = (JSONArray) jsonObj.get("results");
                for (int i = 0; i < jsonObject1.length(); i++) {
                    JSONObject jsonObject2 = (JSONObject) jsonObject1.get(i);
                    String jsonAddress = jsonObject2.getString("formatted_address");
                    String jsonName = jsonObject2.getString("name");
                    JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
                    JSONObject location = (JSONObject) jsonObject3.get("location");

                    String lat = location.get("lat").toString();
                    String lag = location.get("lng").toString();

//                    names.add(jsonName + "");
//                    lati.add(Double.valueOf(lat));
//                    longi.add(Double.valueOf(lag));


                }

//                String result1 = "";

//                for (int i = 0; i < names.size(); i++) {
////                        gMap.addMarker(new MarkerOptions()
////                                .position(new LatLng(lati.get(i), longi.get(i)))
////                                .title(names.get(i)));
////                    result1 += names.get(i) + " " + lati.get(i) + longi.get(i) + "\n";
//                }


            } catch (JSONException e1) {
                e1.printStackTrace();
            }





        }

//        GoogleMap nMap;
//
//
//        nMap.addMarker(new MarkerOptions().position(new LatLng(40, -140.9692)).title("Marker2"));
//        nMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(37.3544, -121.9692)));


    }



}



