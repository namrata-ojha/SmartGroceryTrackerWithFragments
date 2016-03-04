package com.scu.smartgrocerytracker.pantry;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.db_helper.PantryDbUtils;
import com.scu.smartgrocerytracker.shoppingList.ShoppingBagAdapter;

import java.util.List;

/**
 * Created by namrataojha on 2/19/16.
 */
public class PantryFragment extends Fragment {
    ListView pantryListView ;
    SmartGroceryDBHelper dbHelper;
    List<PantryItem> pantryItems;
    static final int REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.inventory_layout, container, false);
        pantryListView = (ListView) rootView.findViewById(R.id.pantryListView);
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        pantryItems = dbHelper.getAllPantryItems();
        pantryListView.setAdapter(new PantryListingAdapter(this.getActivity(), R.layout.pantry_list_row, pantryItems));
        pantryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),UpdatePantryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PantryDbUtils.PANTRY_TABLE_NAME,pantryItems.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("Pantry Fragment", "After invoking UpdatePantryActivity");
            }
        });

        return rootView;
   }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());
        pantryItems = dbHelper.getAllPantryItems();
        pantryListView.setAdapter(new PantryListingAdapter(this.getActivity(), R.layout.pantry_list_row, pantryItems));


    }
}
