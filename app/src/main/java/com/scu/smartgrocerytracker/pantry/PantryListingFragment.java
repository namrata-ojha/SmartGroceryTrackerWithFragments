package com.scu.smartgrocerytracker.pantry;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.db_helper.PantryDbUtils;
import com.scu.smartgrocerytracker.shoppingList.ShoppingBagAdapter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Aparna
 */
public class PantryListingFragment extends Fragment {
    ListView pantryListView ;
    SmartGroceryDBHelper dbHelper;
    List<PantryItem> pantryItems;
    //PantryListingAdapter pantryAdapter;
    Set<Integer> checkedPantryItems = new HashSet<>();
    Button deleteButton;
    static final int REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.inventory_layout, container, false);
        pantryListView = (ListView) rootView.findViewById(R.id.pantryListView);
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        pantryItems = dbHelper.getAllPantryItems();
        PantryListingAdapter pantryAdapter = new PantryListingAdapter(this.getActivity(), R.layout.inventory_list_row, pantryItems,checkedPantryItems);
        pantryListView.setAdapter(pantryAdapter);

        //List view item selection listener
        pantryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), UpdatePantryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PantryDbUtils.PANTRY_TABLE_NAME, pantryItems.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("Pantry Fragment", "After invoking UpdatePantryActivity");
            }
        });


        //Delete button listener
        deleteButton = (Button) rootView.findViewById(R.id.deleteButton);
        if(pantryItems.size() == 0) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedPantryItems.size() == 0) {
                    Toast.makeText(v.getContext(),"No item selected",Toast.LENGTH_SHORT).show();
                    return;
                }
                SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext()).deletePantryItems(checkedPantryItems);
                Toast.makeText(v.getContext(), checkedPantryItems.size() + " item(s) deleted", Toast.LENGTH_SHORT).show();
                checkedPantryItems.clear();
                onResume();
            }
        });

        return rootView;
   }

    @Override
     public void onResume() {
        super.onResume();
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());
        pantryItems = dbHelper.getAllPantryItems();
        checkedPantryItems.clear();
        if(pantryItems.size() == 0) {
            deleteButton.setVisibility(View.GONE);
        }
        else {
            deleteButton.setVisibility(View.VISIBLE);
        }
        PantryListingAdapter pantryAdapter = new PantryListingAdapter(this.getActivity(), R.layout.inventory_list_row, pantryItems,checkedPantryItems);
        pantryListView.setAdapter(pantryAdapter);


    }


}
