package com.scu.smartgrocerytracker.items;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Constants;

import java.util.List;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListingActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    static Items itemClicked;
    SmartGroceryDBHelper dbHelper;
    String itemNameToAdd;
    String categoryToAdd;
    int itemID;
    int quantity;
    ItemListingAdpater itemAdapter;
    String itemPathh;
    Button Start;
    TextView Speech;
    Dialog match_text_dialog;
    ListView textlist;
    ArrayList<String> matches_text;
    SearchView searchv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.listViewItem);
        Start = (Button)findViewById(R.id.start_reg);
        searchv = (SearchView) findViewById(R.id.searchView);
        //Speech = (TextView)findViewById(R.id.speech);
        dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
        //Get  intent value
        Intent intent = getIntent();
      //  TextView itemLabel = (TextView) findViewById(R.id.textViewLabelItem);
        String categoryText = intent.getStringExtra(Constants.CATEGORY_NAME);
//        itemLabel.setText(categoryText);
        dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
        List<Items> item = dbHelper.getItemsByCategory(categoryText);
        itemAdapter = new ItemListingAdpater(this, item);
        listView.setAdapter(itemAdapter);



        Start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_LONG).show();
                }
            }

        });
//
        searchv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.getFilter().filter(newText);
                return false;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked = (Items) parent.getItemAtPosition(position);
                itemNameToAdd = itemClicked.getItemName();
                categoryToAdd = itemClicked.getItemCategory();
                itemID = itemClicked.getId();
                itemPathh = itemClicked.getItemPath();
                quantity = 1;
                dbHelper.insertItemInShoppingList(itemNameToAdd, categoryToAdd, quantity, itemID, itemPathh);

                String toast = "" + itemNameToAdd + " added to shopping List!!!";
                Toast.makeText(ItemListingActivity.this, toast, Toast.LENGTH_LONG).show();
                itemAdapter.getFilter().filter("");
                searchv.setQuery("", false);

            }

        });
    }
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ItemListingActivity.this);
            match_text_dialog.setContentView(R.layout.dialog_matches_frag);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView) match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);
            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                   // Speech.setText("You have said " + matches_text.get(position));

                    searchv.setQuery(matches_text.get(position), false);
                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_activity_menu, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //  int id = item.getItemId();
//        Uri packageURI = Uri.parse("package:edu.scu.smartgrocerytracker");
//
//        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//        startActivity(uninstallIntent);
//
//        return super.onOptionsItemSelected(item);
//    }
}
