package com.scu.smartgrocerytracker.shoppingList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;


import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.items.ItemListingActivity;
import com.scu.smartgrocerytracker.pantry.AddToPantryActivity;
import com.scu.smartgrocerytracker.items.Items;

/**
 *
 */
public class ShoppingListFragment extends Fragment {
    SmartGroceryDBHelper dbHelper;
    ShoppingBagAdapter adapter;
    ListView listView;
    List<Items> shoppedItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.shopping_list, container, false);



        Log.d("shopping Fragment ", "Called");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listView = (ListView) rootView.findViewById(R.id.ShoppingBagListView);
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        shoppedItems = dbHelper.getAllItemsFromShoppingList();
        Log.d("shoppi before fethching", "t");
        for (Items c : shoppedItems) {
            Log.d("Shopping Bag from list", c.getItemName().toString());

        }
        adapter =new ShoppingBagAdapter(this.getActivity(), R.layout.content_shopping_bag_view, shoppedItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Items item = (Items) parent.getItemAtPosition(position);
                Items itemClicked = (Items) parent.getItemAtPosition(position);

                Intent i = new Intent(getActivity().getApplicationContext(), AddToPantryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ITEM_TABLE_NAME,  itemClicked);
                i.putExtras(bundle);
                startActivity(i);

            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        shoppedItems = dbHelper.getAllItemsFromShoppingList();

        adapter =new ShoppingBagAdapter(this.getActivity(), R.layout.content_shopping_bag_view, shoppedItems);

        listView.setAdapter(adapter);

    }
}