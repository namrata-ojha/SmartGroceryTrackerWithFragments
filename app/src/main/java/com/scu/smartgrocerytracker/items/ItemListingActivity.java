package com.scu.smartgrocerytracker.items;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Constants;

import java.util.List;

public class ItemListingActivity extends AppCompatActivity {
    static Items itemClicked;
    SmartGroceryDBHelper dbHelper;
    String itemNameToAdd;
    String categoryToAdd;
    int itemID;
    int quantity;
    ItemListingAdpater itemAdapter;
    String itemPathh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.listViewItem);
        SearchView searchv = (SearchView) findViewById(R.id.searchView);

        dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
        //Get  intent value
        Intent intent = getIntent();
        TextView itemLabel = (TextView) findViewById(R.id.textViewLabelItem);
        String categoryText = intent.getStringExtra(Constants.CATEGORY_NAME);
        itemLabel.setText(categoryText);
        dbHelper = SmartGroceryDBHelper.getInstance(getApplicationContext());
        List<Items> item = dbHelper.getItemsByCategory(categoryText);
        itemAdapter = new ItemListingAdpater(this, item);
        listView.setAdapter(itemAdapter);
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
                itemPathh=itemClicked.getItemPath();
                quantity = 1;
                dbHelper.insertItemInShoppingList(itemNameToAdd, categoryToAdd, quantity, itemID,itemPathh);

                String toast = "" + itemNameToAdd + " added to shopping List!!!";
                Toast.makeText(ItemListingActivity.this, toast, Toast.LENGTH_LONG).show();

            }

        });


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
