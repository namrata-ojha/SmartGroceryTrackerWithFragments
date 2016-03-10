package com.scu.smartgrocerytracker.shoppingList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;


import com.scu.smartgrocerytracker.BarcodeScanner.BarcodeFragment;
import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.pantry.AddToPantryActivity;
import com.scu.smartgrocerytracker.items.Items;

/**
 *
 */
public class ShoppingListFragment extends Fragment {
    SmartGroceryDBHelper dbHelper;
    ShoppingBagAdapter adapter;
    ListView listView;
    List<Items> shoppingBagItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.shopping_list, container, false);
        BarcodeFragment.barcodeToPantry =false;

        Log.d("shopping Fragment ", "Called");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listView = (ListView) rootView.findViewById(R.id.ShoppingBagListView);
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        shoppingBagItems = dbHelper.getAllItemsFromShoppingList();
        Log.d("shoppi before fethching", "t");
        for (Items c : shoppingBagItems) {
            Log.d("Shopping Bag from list", c.getItemName().toString());

        }
        adapter = new ShoppingBagAdapter(this.getActivity(), R.layout.content_shopping_bag_view, shoppingBagItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Items item = (Items) parent.getItemAtPosition(position);
                Items itemClicked = (Items) parent.getItemAtPosition(position);
                String itemName2 = itemClicked.getItemName();
                int itemId2 = itemClicked.getId();
                //call the delete row function
                dbHelper.deleteByNameFromShoppingList(itemName2);

                Intent i = new Intent(getActivity().getApplicationContext(), AddToPantryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ITEM_TABLE_NAME, itemClicked);
                i.putExtras(bundle);
                startActivity(i);

            }
        });
/**
 * share functionality for Shopping Bag Items-Aparna
 */
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());
        shoppingBagItems = dbHelper.getAllItemsFromShoppingList();


        final ImageButton share = (ImageButton) rootView.findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = "";

                for (Items s : shoppingBagItems) {
                    test += s.getItemCategory() + " :" + s.getItemName() + "\n";
                }
                Log.i("Tesitin ToString", test);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, test);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        shoppingBagItems = dbHelper.getAllItemsFromShoppingList();

        adapter = new ShoppingBagAdapter(this.getActivity(), R.layout.content_shopping_bag_view, shoppingBagItems);

        listView.setAdapter(adapter);

    }
}