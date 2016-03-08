package com.scu.smartgrocerytracker.BarcodeScanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.pantry.AddToPantryActivity;
import com.scu.smartgrocerytracker.welcome.WelcomeMainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;


public class BarcodeFragment extends Fragment {
    //For fragments
     ViewGroup rootView ;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    TextView txtProduct;
    TextView txtCost;
    TextView txtBarcode;
    Button scanBarcode;
    Button scanQR;
    Button btnSwitchtoInventory;
    String productName ="";
    double prCost =0.0;
    View textProductView;
    View barcodeView;
    View buttonClickSwitch ;
    View txtcost;
    View textProductViewLabel;
    View txtCostLabel;
    public static  Boolean barcodeToPantry = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = (ViewGroup) inflater.inflate(R.layout.barcode_layout, container, false);
        txtProduct = (TextView)rootView.findViewById(R.id.textViewProductName);
        //txtProduct.setMovementMethod(new ScrollingMovementMethod());

// making widgets  invisble
        textProductView = rootView.findViewById(R.id.textViewProductName);
        textProductView.setVisibility(View.GONE);

        barcodeView = rootView.findViewById(R.id.textViewBarcode);
        barcodeView.setVisibility(View.GONE);

        buttonClickSwitch = rootView.findViewById(R.id.buttonAddToInventory);
        buttonClickSwitch.setVisibility(View.GONE);

        txtcost = rootView.findViewById(R.id.textViewCost);
        txtcost.setVisibility(View.GONE);

        textProductViewLabel = rootView.findViewById(R.id.textViewPrLabel);
        textProductViewLabel.setVisibility(View.GONE);

        txtCostLabel = rootView.findViewById(R.id.textViewCostLabel);
        txtCostLabel.setVisibility(View.GONE);

        txtCost = (TextView)rootView.findViewById(R.id.textViewCost);
        txtBarcode = (TextView)rootView.findViewById(R.id.textViewBarcode);
        scanBarcode =(Button) rootView.findViewById(R.id.scanner2);

        btnSwitchtoInventory =(Button) rootView.findViewById(R.id.buttonAddToInventory);
        //scanQR =(Button) rootView.findViewById(R.id.scanner);
        scanBarcode.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
               scanBar(rootView);

            }
        });

//      //  scanQR.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//                scanQR(rootView);
//
//            }
//        });

        btnSwitchtoInventory.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent i = new Intent(getActivity().getApplicationContext(), AddToPantryActivity.class);
                Bundle bundle = new Bundle();
                barcodeToPantry =true;
                bundle.putSerializable("product",  productName);
                bundle.putSerializable("cost", prCost);

                i.putExtras(bundle);
                startActivity(i);

            }
        });
        return rootView;
    }

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog((Activity) getActivity().getApplicationContext(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog((Activity) getActivity().getApplicationContext(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                barcodeView.setVisibility(View.VISIBLE);
                txtBarcode.setText(contents);
               // Toast toast = Toast.makeText( getActivity().getApplicationContext(), "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
               // toast.show();

                if (isConnected()) {
                    //Toast toast2 = Toast.makeText( getActivity().getApplicationContext(), "Connected:", Toast.LENGTH_LONG);
                   // toast2.show();
                } else {
                    Toast toastNotConnected = Toast.makeText(getActivity().getApplicationContext(), "You are not connected to internet!!!:", Toast.LENGTH_LONG);
                    toastNotConnected.show();

                }


                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute("http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=86121C8B-19AF-458C-A136-2551F48D6799&upc="+contents);
            }
        }
    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {


        private Exception exception;
        private ProgressBar progressBar;
        private TextView responseView;
        private EditText emailText ;
        //String API_URL ="http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=86121C8B-19AF-458C-A136-2551F48D6799&upc=" ;
        // http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=86121C8B-19AF-458C-A136-2551F48D6799&upc=079801003961
        // String barcode="079801003961";





        protected void onPreExecute() {

            //progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            String resString = null;

            return  resString;
        }


        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            //progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            //responseView.setText(response);

        }
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
            if (inputStream != null){
                result = convertInputStreamToString(inputStream);
                Log.d("result",result);}
            else
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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) rootView.getContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            Log.d("network connected","true");
            return true;}
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
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
                Log.d("jsonObj", jsonObj.toString());

                Iterator<String> itr = jsonObj.keys();
                while ( itr.hasNext()){
                    JSONObject jsonObject2 = jsonObj.optJSONObject(itr.next());
                    //  Iterator<String> itr2=jsonObject2.keys();
                    textProductView.setVisibility(View.VISIBLE);
                    txtcost.setVisibility(View.VISIBLE);
                    textProductViewLabel.setVisibility(View.VISIBLE);
                    txtCostLabel.setVisibility(View.VISIBLE);
                    productName = jsonObject2.optString("productname").toString();
                    txtProduct.setText(productName);

                    Log.d("productName", productName);

                    prCost = jsonObject2.optDouble("price");
                    Log.d("prCost",Double.toString(prCost));
                    txtCost.setText(Double.toString(prCost));
                    buttonClickSwitch.setVisibility(View.VISIBLE);
//                    while (itr2.hasNext()) {
//                        Log.d("Inner Keys", itr2.next());
//
//                    }
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }


    }

}
